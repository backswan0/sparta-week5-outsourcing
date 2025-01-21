package com.example.outsourcingproject.domain.review.repository;

import com.example.outsourcingproject.common.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 가게 ID로 해당 가게의 모든 리뷰 조회
    List<Review> findByStoreId(Long storeId);

    // 최소 별점으로 리뷰 조회
    List<Review> findByStoreIdAndRatingGreaterThanEqual(
        Long storeId,
        Integer startRating
    );

    // 최대 별점으로 리뷰 조회
    List<Review> findByStoreIdAndRatingLessThanEqual(
        Long storeId,
        Integer endRating
    );

    // 별점 범위로 리뷰 조회
    List<Review> findByStoreIdAndRatingBetween(
        Long storeId,
        Integer startRating,
        Integer endRating
    );

    // 주문 ID와 손님 ID로 리뷰 조회
    Optional<Review> findByOrderIdAndCustomerId(Long orderId, Long customerId);
}