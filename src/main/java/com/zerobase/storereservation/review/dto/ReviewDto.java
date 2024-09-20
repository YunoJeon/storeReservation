package com.zerobase.storereservation.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long reservationId;
    private Long userId;
    private Long storeId;
    private String content;
    private LocalDateTime createdAt;
}
