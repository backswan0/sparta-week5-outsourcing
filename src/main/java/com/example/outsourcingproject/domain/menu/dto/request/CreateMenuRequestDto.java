package com.example.outsourcingproject.domain.menu.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class CreateMenuRequestDto {

    private final String menuName;
    private final Integer menuPrice;
    private final String menuInfo;
    private final List<String> categoryList;

    public CreateMenuRequestDto(
        String menuName,
        Integer menuPrice,
        String menuInfo,
        List<String> categoryList
    ) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuInfo = menuInfo;
        this.categoryList = categoryList;
    }
}