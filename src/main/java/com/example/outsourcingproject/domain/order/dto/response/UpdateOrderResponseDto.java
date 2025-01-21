package com.example.outsourcingproject.domain.order.dto.response;

import com.example.outsourcingproject.common.entity.Order;
import com.example.outsourcingproject.domain.order.OrderState;
import lombok.Getter;

@Getter
public class UpdateOrderResponseDto {

    private final OrderState updatedOrderState;
    private final Long storeId;

    public UpdateOrderResponseDto(Order order) {
        this.storeId = order.getStore().getId();
        this.updatedOrderState = order.getOrderState();
    }
}