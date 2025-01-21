package com.example.outsourcingproject.domain.order.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.outsourcingproject.common.entity.Order;
import com.example.outsourcingproject.common.entity.Store;
import com.example.outsourcingproject.common.exception.ErrorCode;
import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionFromAcceptedException;
import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionFromPendingException;
import com.example.outsourcingproject.domain.order.OrderState;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @InjectMocks
    private Order order;

    @Test
    void 메서드_테스트_Pending에서_Canceled_입력_시_정상적으로_작동한다() {
        //given
        order = new Order(
            OrderState.PENDING,
            new Store(
                1L,
                "이름",
                "주소",
                "전화번호",
                10000,
                LocalTime.of(10, 0),
                LocalTime.of(23, 0)
//                new Category("메뉴 카테고리 1"),
//                new Category("메뉴 카테고리 2")
            )
        );

        // when
        order.updateOrderStatus(OrderState.CANCELED);

        // then
        assertEquals(OrderState.CANCELED, order.getOrderState());
    }

    @Test
    void 메서드_테스트_Pending에서_Delivering_입력_시_예외가_발생한다() {
        //given
        order = new Order(
            OrderState.PENDING,
            new Store(
                1L,
                "이름",
                "주소",
                "전화번호",
                10000,
                LocalTime.of(10, 0),
                LocalTime.of(23, 0)
//                new Category("메뉴 카테고리 1"),
//                new Category("메뉴 카테고리 2")
            )
        );

        // when
        InvalidTransitionFromPendingException exception = assertThrows(
            InvalidTransitionFromPendingException.class,
            () -> order.updateOrderStatus(OrderState.DELIVERING)
        );

        // then
        assertEquals(ErrorCode.INVALID_PENDING_STATE_TRANSITION, exception.getErrorCode());
    }

    @Test
    void 메서드_테스트_Accepted에서_Delivered_입력_시_예외가_발생한다() {
        //given
        order = new Order(
            OrderState.ACCEPTED,
            new Store(
                1L,
                "이름",
                "주소",
                "전화번호",
                10000,
                LocalTime.of(10, 0),
                LocalTime.of(23, 0)
//                new Category("메뉴 카테고리 1"),
//                new Category("메뉴 카테고리 2")
            )
        );

        // when
        InvalidTransitionFromAcceptedException exception = assertThrows(
            InvalidTransitionFromAcceptedException.class,
            () -> order.updateOrderStatus(OrderState.DELIVERED)
        );

        // then
        assertEquals(ErrorCode.INVALID_ACCEPTED_STATE_TRANSITION, exception.getErrorCode());
    }
}