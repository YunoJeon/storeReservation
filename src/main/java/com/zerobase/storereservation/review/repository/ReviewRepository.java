package com.zerobase.storereservation.review.repository;

import com.zerobase.storereservation.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByStoreId(Long storeId);
}
