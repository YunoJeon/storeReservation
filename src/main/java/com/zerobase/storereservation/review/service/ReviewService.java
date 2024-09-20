package com.zerobase.storereservation.review.service;

import com.zerobase.storereservation.common.exception.CustomException;
import com.zerobase.storereservation.reservation.entity.Reservation;
import com.zerobase.storereservation.reservation.repository.ReservationRepository;
import com.zerobase.storereservation.review.entity.Review;
import com.zerobase.storereservation.review.repository.ReviewRepository;
import com.zerobase.storereservation.store.entity.Store;
import com.zerobase.storereservation.store.repository.StoreRepository;
import com.zerobase.storereservation.user.entity.User;
import com.zerobase.storereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.zerobase.storereservation.common.type.ErrorCode.*;
import static com.zerobase.storereservation.reservation.type.StatusType.VISITED;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 리뷰 등록
    public Review createReview(Long reservationId, Long userId, Long storeId, String content) {

        // 예약자 확인
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        // 방문 status 가 VISITED(키오스크에서 방문체크를 하지 않았으면) 면 리뷰 작성 불가능
        if (!reservation.getStatus().equals(VISITED)) {
            throw new CustomException(NOT_VISITED);
        }

        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 방문한 이용자와 어플 사용자가 다르면 예외 발생
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new CustomException(NOT_VISITED);
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        Review review = Review.builder()
                .user(user)
                .store(store)
                .reservation(reservation)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        return review;
    }

    // 리뷰 수정
    public void updateReview(Long reviewId, Long userId, String content) {

        // 작성된 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_REVIEW));

        // 리뷰 작성자와 수정하려는 사용자가 다르면 접근 거부
        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ACCESS_DENIED);
        }

        review.setContent(content);
        reviewRepository.save(review);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId, Long userId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_REVIEW));

        User reviewAuthor = review.getUser();
        User requestUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 리뷰 작성자가 아니고, 해당 리뷰가 등록된 매장의 관리자가 아니면 접근 거부 (삭제는 리뷰 작성자와 매장 관리자 가능)
        if (!requestUser.equals(reviewAuthor)) {
            if (!storeRepository.findById(review.getStore().getId())
                    .orElseThrow(() -> new CustomException(NOT_FOUND_STORE))
                    .getUser().equals(requestUser)) {
                throw new CustomException(ACCESS_DENIED);
            }
        }

        reviewRepository.delete(review);
    }
}
