package com.example.outsourcingproject.domain.order.repository;

import com.example.outsourcingproject.common.entity.Order;
import com.example.outsourcingproject.domain.order.OrderState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndOrderStateNot(
        Long id,
        OrderState status
    );

    List<Order> findAllByStoreId(Long storeId);

}