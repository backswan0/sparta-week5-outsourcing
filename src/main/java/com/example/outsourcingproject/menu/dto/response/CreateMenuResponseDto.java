package com.example.outsourcingproject.menu.dto.response;

import com.example.outsourcingproject.entity.Menu;
import com.example.outsourcingproject.entity.MenuCategory;
import lombok.Getter;

@Getter
public class CreateMenuResponseDto {

    private final Long id;
    private final String menuName;
    private final Integer menuPrice;
    private final String menuInfo;

    public CreateMenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.menuInfo = menu.getMenuInfo();
    }
}