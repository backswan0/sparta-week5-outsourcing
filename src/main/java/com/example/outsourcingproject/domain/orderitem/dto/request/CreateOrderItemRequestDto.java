package com.example.outsourcingproject.domain.orderitem.dto.request;

import lombok.Getter;

@Getter
public class CreateOrderItemRequestDto {

    private final Long menuId;
    private final Integer eachAmount;

    public CreateOrderItemRequestDto(
        Long menuId,
        Integer eachAmount
    ) {
        this.menuId = menuId;
        this.eachAmount = eachAmount;
    }
}