package com.example.outsourcingproject.domain.order;

import com.example.outsourcingproject.common.exception.badrequest.InvalidOrderStateException;
import java.util.Arrays;

public enum OrderState {
    PENDING, // 주문 생성 시, 즉 수락 대기 상태
    ACCEPTED, // 주문 수락
    CANCELED, // 주문 취소
    DELIVERING, // ACCEPTED 이후 배달 중
    DELIVERED; // DELIVERING 이후 배달 완료

    // 기능: 입력된 문자열을 바탕으로 OrderStatus 값을 찾는 메서드
    public static OrderState of(String orderState) {
        return Arrays.stream(OrderState.values())
            .filter(
                status -> status
                    .name()
                    .equalsIgnoreCase(orderState))
            .findFirst()
            .orElseThrow(InvalidOrderStateException::new);
    }
}