package com.example.outsourcingproject.domain.order.service;

import com.example.outsourcingproject.domain.order.dto.request.UpdateOrderRequestDto;
import com.example.outsourcingproject.domain.order.dto.response.OrderResponseDto;
import com.example.outsourcingproject.domain.order.dto.response.UpdateOrderResponseDto;
import java.util.List;

public interface OrderService {

    UpdateOrderResponseDto updateOrderStatus(UpdateOrderRequestDto requestDto);

    List<OrderResponseDto> readAllOrdersFromStores();

    List<OrderResponseDto> readAllOrdersByStoreId(Long storeId);

}