package com.zerobase.storereservation.review.controller;

import com.zerobase.storereservation.review.dto.ReviewDto;
import com.zerobase.storereservation.review.entity.Review;
import com.zerobase.storereservation.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // http://localhost:8080/review
    @Operation(summary = "리뷰 등록", description = "리뷰 등록 API 입니다. reservation 테이블에 status 컬럼이 \"VISITED\" 인 유저만 리뷰 등록이 가능합니다.")
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @RequestBody ReviewDto reviewDto) {

        Review review = reviewService.createReview(
                reviewDto.getReservationId(),
                reviewDto.getUserId(),
                reviewDto.getStoreId(),
                reviewDto.getContent()
        );

        ReviewDto responseDto = new ReviewDto(
                review.getReservation().getId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getContent(),
                review.getCreatedAt()
        );

        return ResponseEntity.ok(responseDto);
    }

    // http://localhost:8080/review/(reviewId)?userId=(userId)&content=(updateContent)
    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 수정 API 입니다. 리뷰 등록한 계정만 수정이 가능합니다.")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId,
            @RequestParam String content) {

        reviewService.updateReview(reviewId, userId, content);
        return ResponseEntity.noContent().build();
    }

    // http://localhost:8080/review/(reviewId)?userId=(userId)
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제 API 입니다. 리뷰 등록한 계정과 매장 관리자만 삭제가 가능합니다.")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId) {

        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }
}
