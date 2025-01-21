package com.example.outsourcingproject.domain.store.dto.response;

import com.example.outsourcingproject.common.entity.StoreCategory;
import lombok.Getter;

@Getter
public class StoreCategoryResponseDto {

    private final String name;

    public StoreCategoryResponseDto(StoreCategory storeCategory) {
        this.name = storeCategory.getName();
    }
}