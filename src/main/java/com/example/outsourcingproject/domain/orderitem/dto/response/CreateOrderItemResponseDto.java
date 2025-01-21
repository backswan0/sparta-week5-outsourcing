package com.example.outsourcingproject.domain.orderitem.dto.response;

import com.example.outsourcingproject.common.entity.OrderItem;
import lombok.Getter;

@Getter
public class CreateOrderItemResponseDto {

    private final Long id;
    private final Long menuId;
    private final String menuName;
    private final Integer eachAmount;
    private final Integer eachPrice;
    private final Integer totalPrice;


    public CreateOrderItemResponseDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.menuId = orderItem.getMenu().getId();
        this.menuName = orderItem.getMenuName();
        this.eachAmount = orderItem.getEachAmount();
        this.eachPrice = orderItem.getMenu().getMenuPrice();
        this.totalPrice = orderItem.getTotalPrice();

    }
}