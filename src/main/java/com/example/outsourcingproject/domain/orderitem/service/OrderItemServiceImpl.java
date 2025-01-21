package com.example.outsourcingproject.domain.orderitem.service;

import com.example.outsourcingproject.domain.orderitem.CreateOrderItemWrapper;
import com.example.outsourcingproject.domain.orderitem.ReadOrderItemWrapper;
import com.example.outsourcingproject.common.entity.Menu;
import com.example.outsourcingproject.common.entity.Order;
import com.example.outsourcingproject.common.entity.OrderItem;
import com.example.outsourcingproject.common.entity.Store;
import com.example.outsourcingproject.common.exception.badrequest.BelowMinimumPurchaseException;
import com.example.outsourcingproject.common.exception.badrequest.InvalidOrderTimeException;
import com.example.outsourcingproject.common.exception.notfound.MenuNotFoundException;
import com.example.outsourcingproject.common.exception.notfound.OrderNotFoundException;
import com.example.outsourcingproject.common.exception.notfound.StoreNotFoundException;
import com.example.outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.outsourcingproject.domain.order.OrderState;
import com.example.outsourcingproject.domain.order.repository.OrderRepository;
import com.example.outsourcingproject.domain.orderitem.dto.request.CreateOrderItemRequestDto;
import com.example.outsourcingproject.domain.orderitem.dto.response.CreateOrderItemResponseDto;
import com.example.outsourcingproject.domain.orderitem.dto.response.OrderItemResponseDto;
import com.example.outsourcingproject.domain.orderitem.repository.OrderItemRepository;
import com.example.outsourcingproject.domain.store.repository.StoreRepository;
import com.example.outsourcingproject.common.utils.SlackSendMessage;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final SlackSendMessage slackSendMessage;

    @Transactional
    @Override
    public CreateOrderItemWrapper createOrderItem(
        Long storeId,
        List<CreateOrderItemRequestDto> requestDtoList
    ) {
        Store foundStore = storeRepository.findById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        LocalTime timeToOrder = LocalTime.now();
        boolean isBeforeOpensAt = timeToOrder.isBefore(foundStore.getOpensAt());
        boolean isAfterClosesAt = timeToOrder.isAfter(foundStore.getClosesAt());

        if (isBeforeOpensAt || isAfterClosesAt) {
            throw new InvalidOrderTimeException();
        }

        Order orderToSave = new Order(
            OrderState.PENDING,
            foundStore
        );

        Order savedOrder = orderRepository.save(orderToSave);

        List<CreateOrderItemResponseDto> responseDtoList = new ArrayList<>();

        responseDtoList = requestDtoList.stream()
            .map(requestDto -> {
                    Menu foundMenu = menuRepository.findById(requestDto.getMenuId())
                        .orElseThrow(MenuNotFoundException::new);

                    OrderItem orderItemToSave = new OrderItem(
                        savedOrder,
                        foundMenu,
                        requestDto.getEachAmount()
                    );

                    OrderItem savedOrderItem = orderItemRepository.save(orderItemToSave);

                    return new CreateOrderItemResponseDto(savedOrderItem);
                }
            ).toList();

        Integer totalPriceSum = responseDtoList.stream()
            .mapToInt(CreateOrderItemResponseDto::getTotalPrice)
            .sum();

        boolean isBelowMinimumPurchase = totalPriceSum < foundStore.getMinimumPurchase();

        if (isBelowMinimumPurchase) {
            throw new BelowMinimumPurchaseException();
        }

        Integer totalAmountSum = responseDtoList.stream()
            .mapToInt(CreateOrderItemResponseDto::getEachAmount)
            .sum();

        savedOrder.updateTotals(totalAmountSum, totalPriceSum);

        orderRepository.save(savedOrder);

        // Slack 알림 api로 가게명과 주문 상태를 전송
        String storeName = savedOrder.getStore().getStoreName();

        OrderState orderState = savedOrder.getOrderState();

        slackSendMessage.callSlackSendMessageApi(storeName, orderState);

        return new CreateOrderItemWrapper(
            responseDtoList,
            totalAmountSum,
            totalPriceSum,
            savedOrder
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ReadOrderItemWrapper readAllOrderItemsByOrderId(Long orderId) {

        Order foundOrder = orderRepository.findById(orderId)
            .orElseThrow(OrderNotFoundException::new);

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList = orderItemRepository.findAllByOrderId(foundOrder.getId());

        List<OrderItemResponseDto> responseDtoList = new ArrayList<>();

        responseDtoList = orderItemList.stream()
            .map(item -> new OrderItemResponseDto(
                    item.getId(),
                    item.getEachAmount(),
                    item.getTotalPrice()
                )
            ).toList();

        return new ReadOrderItemWrapper(
            responseDtoList,
            foundOrder.getTotalAmountSum(),
            foundOrder.getTotalPriceSum(),
            foundOrder.getId(),
            foundOrder.getOrderState()
        );
    }
}