package com.example.outsourcingproject.common.aspect;

import com.example.outsourcingproject.domain.orderitem.CreateOrderItemWrapper;
import com.example.outsourcingproject.domain.order.OrderState;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OrderItemAspect {

    @Pointcut("execution(* com.example.outsourcingproject.domain.orderitem.service.OrderItemServiceImpl.createOrderItem(..))")
    public void trackOrderItemServiceMethods() {
    }

    @Around("trackOrderItemServiceMethods()")
    public Object trackCreateOrderItem(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        log.info("{} method starts", methodName);

        Object result = joinPoint.proceed();

        CreateOrderItemWrapper wrapper = (CreateOrderItemWrapper) result;

        Long storeId = wrapper.getStoreId();
        Long orderId = wrapper.getOrderId();
        LocalDateTime now = LocalDateTime.now();
        OrderState orderState = wrapper.getOrderState();

        log.info("Store ID {}", storeId);
        log.info("Order ID: {}", orderId);
        log.info("Ordered At: {}", now);
        log.info("Current Order State: {}", orderState);

        return result;
    }
}