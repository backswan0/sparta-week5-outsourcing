package com.example.outsourcingproject.store.dto.response;

import com.example.outsourcingproject.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final String name;

    public CategoryResponseDto(Category category) {
        this.name = category.getName();
    }
}
