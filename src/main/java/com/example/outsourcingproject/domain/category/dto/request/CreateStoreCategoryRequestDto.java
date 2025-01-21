package com.example.outsourcingproject.domain.category.dto.request;

import lombok.Getter;

@Getter
public class CreateStoreCategoryRequestDto {

    private final String name;

    public CreateStoreCategoryRequestDto(String name) {
        this.name = name;

    }
}
