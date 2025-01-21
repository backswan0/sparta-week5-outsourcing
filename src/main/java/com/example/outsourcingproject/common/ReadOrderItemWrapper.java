package com.example.outsourcingproject.common;


import com.example.outsourcingproject.domain.order.OrderState;
import com.example.outsourcingproject.domain.orderitem.dto.response.OrderItemResponseDto;
import java.util.List;
import lombok.Getter;

@Getter
public class ReadOrderItemWrapper {

    private final List<OrderItemResponseDto> orderDetails;
    private final Integer totalAmountSum;
    private final Integer totalPriceSum;
    private final Long orderId;
    private final OrderState orderState;

    public ReadOrderItemWrapper(
        List<OrderItemResponseDto> orderDetails,
        Integer totalAmountSum,
        Integer totalPriceSum,
        Long orderId,
        OrderState orderState
    ) {
        this.orderDetails = orderDetails;
        this.totalAmountSum = totalAmountSum;
        this.totalPriceSum = totalPriceSum;
        this.orderId = orderId;
        this.orderState = orderState;
    }
}
