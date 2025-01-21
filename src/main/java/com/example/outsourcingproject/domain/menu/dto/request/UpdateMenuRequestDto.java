package com.example.outsourcingproject.domain.menu.dto.request;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class UpdateMenuRequestDto {

    @Nullable
    private final String menuName;

    @Nullable
    private final Integer menuPrice;

    @Nullable
    private final String menuInfo;

    public UpdateMenuRequestDto(
        @Nullable String menuName,
        @Nullable Integer menuPrice,
        @Nullable String menuInfo
    ) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuInfo = menuInfo;
    }
}
