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
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "order_items")
@Getter
public class OrderItem {

    @Comment("주문 아이템 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("주문한 메뉴의 각 수량")
    @Column(
        name = "each_amount",
        nullable = false
    )
    private Integer eachAmount;

    @Comment("주문한 메뉴의 총 가격")
    @Column(
        name = "total_price",
        nullable = false
    )
    private Integer totalPrice;

    @Comment("메뉴 이름")
    @Column(
        name = "menu_name",
        nullable = false
    )
    private String menuName;

    @Comment("메뉴 가격")
    @Column(
        name = "menu_price",
        nullable = false
    )
    private Integer menuPrice;

    @Comment("메뉴 정보")
    @Column(
        name = "menu_info",
        nullable = false
    )
    private String menuInfo;

    @Comment("메뉴 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "menu_id",
        nullable = false
    )
    private Menu menu;

    @Comment("주문 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "order_id",
        nullable = false
    )
    private Order order;

    protected OrderItem() {
    }

    public OrderItem(
        Order order,
        Menu menu,
        Integer eachAmount
    ) {
        this.order = order;
        this.menu = menu;
        this.menuName = menu.getMenuName();
        this.menuInfo = menu.getMenuInfo();
        this.menuPrice = menu.getMenuPrice();
        this.eachAmount = eachAmount;
        this.totalPrice = calculateTotalPrice(eachAmount, menu.getMenuPrice());
    }

    private Integer calculateTotalPrice(
        Integer eachAmount,
        Integer eachPrice
    ) {
        return eachPrice * eachAmount;
    }
}