package com.example.outsourcingproject.domain.category.dto.request;

import lombok.Getter;

@Getter
public class CreateMenuCategoryRequestDto {

    private final String name;

    public CreateMenuCategoryRequestDto(String name) {
        this.name = name;
    }
}
