package com.example.outsourcingproject.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "REVIEWS")
@Getter
public class Review extends BaseEntity {

    @Comment("리뷰 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("리뷰 별점(5점 만점)")
    @Column(
        name = "rating",
        nullable = false
    )
    @NotNull
    @Min(value = 1, message = "별점은 1점 이상이어야 합니다.") // 최소값 1
    @Max(value = 5, message = "별점은 5점 이하여야 합니다.") // 최대값 5
    private Integer rating;

    @Comment("리뷰 내용")
    @Column(
        name = "contents",
        nullable = false
    )
    private String contents;

    @Comment("손님 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "customer_id",
        nullable = false
    )
    private Customer customer;

    @Comment("주문 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "order_id",
        nullable = false
    )
    private Order order;

    @Comment("가게 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "store_id",
        nullable = false
    )
    private Store store;

    protected Review() {
    }

    public Review(Customer customer, Store store, Order order, String contents, Integer rating) {
        this.customer = customer;
        this.store = store;
        this.order = order;
        this.contents = contents;
        this.rating = rating;
    }
}
