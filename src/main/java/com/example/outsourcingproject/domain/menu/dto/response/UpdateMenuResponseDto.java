package com.example.outsourcingproject.domain.menu.dto.response;

import lombok.Getter;

@Getter
public class UpdateMenuResponseDto {

    private final String menuName;
    private final Integer menuPrice;
    private final String menuInfo;

    public UpdateMenuResponseDto(
        String menuName,
        Integer menuPrice,
        String menuInfo
    ) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuInfo = menuInfo;
    }
}