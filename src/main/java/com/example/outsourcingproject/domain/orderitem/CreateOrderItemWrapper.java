package com.example.outsourcingproject.domain.orderitem;

import com.example.outsourcingproject.common.entity.Order;
import com.example.outsourcingproject.domain.order.OrderState;
import com.example.outsourcingproject.domain.orderitem.dto.response.CreateOrderItemResponseDto;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateOrderItemWrapper {

    private final List<CreateOrderItemResponseDto> orderDetails;
    private final Integer totalAmountSum;
    private final Integer totalPriceSum;
    private final Long storeId;
    private final Long orderId;
    private final OrderState orderState;

    public CreateOrderItemWrapper(
        List<CreateOrderItemResponseDto> orderDetails,
        Integer totalAmountSum,
        Integer totalPriceSum,
        Order order
    ) {
        this.orderDetails = orderDetails;
        this.totalAmountSum = totalAmountSum;
        this.totalPriceSum = totalPriceSum;
        this.storeId = order.getStore().getId();
        this.orderId = order.getId();
        this.orderState = order.getOrderState();
    }
}