package com.zerobase.storereservation.reservation.service;

import com.zerobase.storereservation.common.exception.CustomException;
import com.zerobase.storereservation.reservation.dto.ReservationDto;
import com.zerobase.storereservation.reservation.entity.Reservation;
import com.zerobase.storereservation.reservation.repository.ReservationRepository;
import com.zerobase.storereservation.reservation.type.StatusType;
import com.zerobase.storereservation.store.entity.Store;
import com.zerobase.storereservation.store.repository.StoreRepository;
import com.zerobase.storereservation.user.entity.User;
import com.zerobase.storereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.zerobase.storereservation.common.type.ErrorCode.*;
import static com.zerobase.storereservation.reservation.type.StatusType.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 예약 등록
    public ReservationDto createReservation(ReservationDto reservationDto, Long userId) {

        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 매장 확인
        Store store = storeRepository.findById(reservationDto.getStoreId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        LocalDateTime reservationTime = reservationDto.getReservationTime();
        LocalDateTime now = LocalDateTime.now();

        // 예약 요청값(parameter) 이 현재 시간 이전이면 예약 불가능(요청이 잘못되었다고 판단.)
        if (reservationTime.isBefore(now)) {
            throw new CustomException(INVALID_REQUEST);
        }

        // 예약 요청한 시간에 이미 예약이 있을경우 예약 불가능(매장의 상황이나 어플리케이션 정책에 따라 달라질 수 있는 부분)
        if (reservationRepository.existsByStoreAndReservationTime(store,
                reservationDto.getReservationTime())) {
            throw new CustomException(ALREADY_RESERVATION);
        }

        Reservation reservation = reservationRepository.save(Reservation.builder()
                .user(user)
                .store(store)
                .reservationTime(reservationDto.getReservationTime())
                .status(PENDING)
                .build());

        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setStoreId(reservation.getStore().getId());
        dto.setStoreName(reservation.getStore().getStoreName());
        dto.setReservationTime(reservation.getReservationTime());
        dto.setStatus(reservation.getStatus());

        return dto;
    }

    // 매장 관리자(파트너) 의 예약 확인 CONFIRMED, CANCELLED 두가지 parameter 전달 가능
    public ReservationDto updateReservationStatus(
            Long reservationId, Long userId, StatusType status) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        // 해당 예약건의 매장 관리자가 아니면 접근 거부
        Store store = reservation.getStore();
        if (!store.getUser().getId().equals(userId)) {
            throw new CustomException(ACCESS_DENIED);
        }

        reservation.setStatus(status);

        reservationRepository.save(reservation);

        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setStoreId(reservation.getStore().getId());
        dto.setStoreName(reservation.getStore().getStoreName());
        dto.setReservationTime(reservation.getReservationTime());
        dto.setStatus(reservation.getStatus());

        return dto;
    }

    // 예약이 CONFIRMED 상태인 예약자가 예약 10분전에 매장에 도착해서 상태를 변경할수 있음
    public void checkIn(Long reservationId, String userPhoneNumber) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        // CONFIRMED 상태가 아니면 매장 관리자가 아직 예약을 확인 못했다고 판단. 혹은 취소상태
        if (reservation.getStatus() != CONFIRMED) {
            throw new CustomException(NOT_CHECKED_RESERVATION);
        }

        LocalDateTime reservationTime = reservation.getReservationTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(reservationTime, now);

        // 매장 이용시간 10분전부터 키오스크에서 체크인을 할 수 있음.
        if (duration.toMinutes() < -10) {
            throw new CustomException(EARLY_TIME);
        }

        // 예약 시간 이후에 키오스크에서 접근하면 예외발생. 매장 직원이나 관리자에게 별도 문의
        if (duration.toMinutes() > 0) {
            throw new CustomException(BEFORE_RESERVATION);
        }

        User user = reservation.getUser();

        // 키오스크에서 예약번호와 휴대폰 번호를 parameter 로 전달받는데, 휴대폰 번호가 틀릴 경우 예외 발생
        if (!user.getPhoneNumber().equals(userPhoneNumber)) {
            throw new CustomException(NOT_FOUND_USER);
        }

        reservation.setStatus(VISITED);
        reservationRepository.save(reservation);
    }
}
