package com.example.outsourcingproject.domain.review.controller;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.domain.review.dto.request.CreateReviewRequestDto;
import com.example.outsourcingproject.domain.review.dto.response.CreateReviewResponseDto;
import com.example.outsourcingproject.domain.review.dto.response.FindReviewResponseDto;
import com.example.outsourcingproject.domain.review.service.ReviewServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    public ReviewController(ReviewServiceImpl reviewServiceImpl) {
        this.reviewServiceImpl = reviewServiceImpl;
    }

    @AuthCheck("CUSTOMER")
    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<CreateReviewResponseDto> createReview(
        @PathVariable("orderId") Long orderId,
        @Valid @RequestBody CreateReviewRequestDto requestDto,
        @RequestHeader("Authorization") String token //todo @Valid 유효성 검사
    ) {
        CreateReviewResponseDto responseDto = reviewServiceImpl.createReviewService(
            orderId,
            requestDto,
            token
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 리뷰 다건 조회(가게 정보 기준, 최신순 정렬 및 별점 범위 필터링 기능)
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<List<FindReviewResponseDto>> findAllReview(
        @PathVariable("storeId") Long storeId,
        @RequestParam(value = "sort", required = false, defaultValue = "latest") String sort,
        @RequestParam(value = "startRating", required = false) Integer startRating,
        @RequestParam(value = "endRating", required = false) Integer endRating
    ) {
        List<FindReviewResponseDto> findReviewResponseDtoList = reviewServiceImpl
            .findAllReviewService(
                storeId,
                sort,
                startRating,
                endRating
            );

        return new ResponseEntity<>(findReviewResponseDtoList, HttpStatus.OK);
    }
}
