package com.example.outsourcingproject.common.entity;

import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionFromAcceptedException;
import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionFromCanceledException;
import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionFromDeliveringException;
import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionFromPendingException;
import com.example.outsourcingproject.domain.order.OrderState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

    @Comment("주문 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("주문 상태")
    @Enumerated(EnumType.STRING)
    @Column(
        name = "order_status",
        nullable = false
    )
    private OrderState orderState;

    @Comment("주문 총 수량")
    @Column(
        name = "total_amount_sum",
        nullable = false
    )
    private Integer totalAmountSum = 0;

    @Comment("주문 총가격")
    @Column(
        name = "total_price_sum",
        nullable = false
    )
    private Integer totalPriceSum = 0;

    @Comment("가게")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "store_id",
        nullable = false
    )
    private Store store;

    protected Order() {
    }

    public Order(
        OrderState orderState,
        Store store
    ) {
        this.orderState = orderState;
        this.store = store;
    }

    public void updateTotals(
        Integer totalAmountSum,
        Integer totalPriceSum
    ) {
        this.totalAmountSum = totalAmountSum;
        this.totalPriceSum = totalPriceSum;
    }

    // 주문 상태를 변경하는 기능
    public void updateOrderStatus(OrderState orderState) {
        validateStatusSequence(orderState);
        this.orderState = orderState;
    }

    // 주문 상태 변경 순서가 올바른지 검증하는 기능
    private void validateStatusSequence(OrderState orderState) {

        boolean isNotAcceptedOrCanceled = !(orderState.equals(OrderState.ACCEPTED)
            || orderState.equals(OrderState.CANCELED));

        boolean isNotDeliveringFromAccepted = !orderState.equals(OrderState.DELIVERING);

        boolean isNotDeliveredFromDelivering = !orderState.equals(OrderState.DELIVERED);

        switch (this.orderState) {
            case PENDING:
                if (isNotAcceptedOrCanceled) {
                    throw new InvalidTransitionFromPendingException();
                }
                break;
            case ACCEPTED:
                if (isNotDeliveringFromAccepted) {
                    throw new InvalidTransitionFromAcceptedException();
                }
                break;
            case CANCELED:
                throw new InvalidTransitionFromCanceledException();
            case DELIVERING:
                if (isNotDeliveredFromDelivering) {
                    throw new InvalidTransitionFromDeliveringException();
                }
                break;
        }
    }
}