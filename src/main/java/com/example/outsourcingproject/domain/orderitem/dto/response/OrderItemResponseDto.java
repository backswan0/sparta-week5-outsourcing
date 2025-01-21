package com.example.outsourcingproject.domain.orderitem.dto.response;

import lombok.Getter;

@Getter
public class OrderItemResponseDto {

    private final Long menuId;
    private final Integer amount;
    private final Integer totalPrice;

    public OrderItemResponseDto(
        Long menuId,
        Integer amount,
        Integer totalPrice
    ) {
        this.menuId = menuId;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }
}
