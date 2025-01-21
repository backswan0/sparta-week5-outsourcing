package com.example.outsourcingproject.domain.orderitem.controller;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.common.CreateOrderItemWrapper;
import com.example.outsourcingproject.common.ReadOrderItemWrapper;
import com.example.outsourcingproject.domain.orderitem.dto.request.CreateOrderItemRequestDto;
import com.example.outsourcingproject.domain.orderitem.service.OrderItemServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemServiceImpl orderItemService;

    @AuthCheck("CUSTOMER")
    @PostMapping("/{storeId}")
    public ResponseEntity<CreateOrderItemWrapper> createOrderItem(
        @PathVariable("storeId") Long storeId,
        @RequestBody List<CreateOrderItemRequestDto> requestDtoList
    ) {
        CreateOrderItemWrapper responseDtoWrapper = orderItemService.createOrderItem(
            storeId,
            requestDtoList
        );

        return new ResponseEntity<>(responseDtoWrapper, HttpStatus.CREATED);
    }

    @AuthCheck("OWNER")
    @GetMapping("/each/{orderId}")
    public ResponseEntity<ReadOrderItemWrapper> readAllOrderItems(
        @PathVariable("orderId") Long orderId
    ) {
        ReadOrderItemWrapper responseDtoWrapper = orderItemService.readAllOrderItemsByOrderId(
            orderId);

        return new ResponseEntity<>(responseDtoWrapper, HttpStatus.OK);
    }
}