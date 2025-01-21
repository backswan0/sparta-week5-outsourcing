package com.example.outsourcingproject.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
// JSON 출력 순서 지정
@JsonPropertyOrder({"id", "contents", "rating", "createdAt"})
public class CreateReviewResponseDto {

    private final Long id;
    private final String contents;
    private final Integer rating;
    private final LocalDateTime createdAt;

    public CreateReviewResponseDto(
        Long reviewId,
        String contents,
        Integer rating,
        LocalDateTime createdAt
    ) {
        this.id = reviewId;
        this.contents = contents;
        this.rating = rating;
        this.createdAt = createdAt;
    }


}
