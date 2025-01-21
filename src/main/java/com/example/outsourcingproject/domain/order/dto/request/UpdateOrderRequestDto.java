package com.example.outsourcingproject.domain.order.dto.request;

import lombok.Getter;

@Getter
public class UpdateOrderRequestDto {

    private final Long id;
    private final String orderState;

    public UpdateOrderRequestDto(
        Long id,
        String orderState
    ) {
        this.id = id;
        this.orderState = orderState;
    }
}