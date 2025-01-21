package com.example.outsourcingproject.domain.store.dto;

import com.example.outsourcingproject.common.entity.Menu;
import lombok.Getter;

@Getter
public class MenuDto {

    private final Long id;
    private final String menuName;
    private final Integer menuPrice;
    private final String menuInfo;

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.menuInfo = menu.getMenuInfo();
    }
}
