package com.example.outsourcingproject.domain.orderitem.service;

import com.example.outsourcingproject.domain.orderitem.CreateOrderItemWrapper;
import com.example.outsourcingproject.domain.orderitem.ReadOrderItemWrapper;
import com.example.outsourcingproject.domain.orderitem.dto.request.CreateOrderItemRequestDto;
import java.util.List;

public interface OrderItemService {

    CreateOrderItemWrapper createOrderItem(
        Long storeId,
        List<CreateOrderItemRequestDto> requestDtoList
    );

    ReadOrderItemWrapper readAllOrderItemsByOrderId(Long orderId);
}