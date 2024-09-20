package com.zerobase.storereservation.reservation.controller;

import com.zerobase.storereservation.reservation.dto.ReservationDto;
import com.zerobase.storereservation.reservation.service.ReservationService;
import com.zerobase.storereservation.reservation.type.StatusType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    // http://localhost:8080/reservation/create?userId=(userId)
    @Operation(summary = "예약 등록", description = "예약 등록 API 입니다. 기본 status 는 \"PENDING\" 입니다.")
    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createReservation(
            @RequestParam Long userId,
            @RequestBody ReservationDto reservationDto) {

        return ResponseEntity.ok(
                reservationService.createReservation(reservationDto, userId));
    }

    // http://localhost:8080/reservation/(reservationId)/status?userId=(userId)&status=(statusType)
    @Operation(summary = "예약 상태 변경", description = "예약 상태 변경 API 입니다. 해당 매장의 관리자가 \"CONFIRMED\", \"CANCELLED\" 등 상태를 변경할 수 있습니다.")
    @PutMapping("/{id}/status")
    public ResponseEntity<ReservationDto> updateReservationStatus(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam StatusType status) {

        return ResponseEntity.ok(
                reservationService.updateReservationStatus(id, userId, status));
    }

    // http://localhost:8080/reservation/(reservationId)/check-in
    @Operation(summary = "예약 방문 확인", description = "예약 방문 확인 API 입니다. 예약을 진행한(로그인 한) 휴대폰번호로 인증하며, 예약시간 10분전 부터 변경이 가능하며, \"VISITED\"로 status 가 변경됩니다. 예약 시간이 지나면 예외가 발생합니다.")
    @PutMapping("/{reservationId}/check-in")
    public ResponseEntity<String> checkIn(
            @PathVariable Long reservationId,
            @RequestParam String userPhoneNumber) {

        reservationService.checkIn(reservationId, userPhoneNumber);

        return ResponseEntity.ok("방문 확인이 완료되었습니다.");
    }

}
