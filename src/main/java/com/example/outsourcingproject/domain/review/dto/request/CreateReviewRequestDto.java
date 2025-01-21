package com.example.outsourcingproject.domain.review.dto.request;

import lombok.Getter;

@Getter
public class CreateReviewRequestDto {

    private String contents;
    private Integer rating;

    public CreateReviewRequestDto() {
    }

    public CreateReviewRequestDto(String contents, int rating) {
        this.contents = contents;
        this.rating = rating;
    }
}
