package com.example.outsourcingproject.domain.review.dto.response;

import com.example.outsourcingproject.common.entity.Review;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
// JSON 출력 순서 지정
@JsonPropertyOrder({"id", "contents", "rating", "createdAt"})
public class FindReviewResponseDto {

    private final Long id;
    private final String contents;
    private final Integer rating;
    private final LocalDateTime createdAt;

    public FindReviewResponseDto(Review review) {
        this.id = review.getId();
        this.contents = review.getContents();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();
    }

}
