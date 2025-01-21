package com.example.outsourcingproject.domain.order.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.outsourcingproject.common.entity.Customer;
import com.example.outsourcingproject.common.entity.Order;
import com.example.outsourcingproject.common.entity.Review;
import com.example.outsourcingproject.common.entity.Store;

public class ReviewTest {

    private Customer customer;
    private Store store;
    private Order order;

    @BeforeEach
    public void setUp() {
        customer = mock(Customer.class);
        store = mock(Store.class);
        order = mock(Order.class);
    }

    @Test
    public void 리뷰_생성_테스트() {
        String contents = "맛있어요!";
        int rating = 5;

        Review review = new Review(customer, store, order, contents, rating);

        assertEquals(customer, review.getCustomer());
        assertEquals(store, review.getStore());
        assertEquals(order, review.getOrder());
        assertEquals(contents, review.getContents());
        assertEquals(rating, review.getRating());
    }
}
