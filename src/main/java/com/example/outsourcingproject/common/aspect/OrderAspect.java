package com.example.outsourcingproject.common.aspect;

import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionException;
import com.example.outsourcingproject.domain.order.OrderState;
import com.example.outsourcingproject.domain.order.dto.request.UpdateOrderRequestDto;
import com.example.outsourcingproject.domain.order.dto.response.UpdateOrderResponseDto;
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
public class OrderAspect {

    @Pointcut("execution(* com.example.outsourcingproject.domain.order.service.OrderServiceImpl.updateOrderStatus(..))")
    public void trackOrderServiceMethods() {
    }

    @Around("trackOrderServiceMethods()")
    public Object trackOrderStatusUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("{} method starts", methodName);

        UpdateOrderRequestDto requestDto = (UpdateOrderRequestDto) args[0];
        Long orderId = requestDto.getId();

        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (InvalidTransitionException ex) {
            log.error(
                "Invalid Transition Exception occurred while executing method {} with Order ID: {}",
                methodName,
                orderId,
                ex
            );
            throw ex;
        }
        UpdateOrderResponseDto responseDto = (UpdateOrderResponseDto) result;

        Long storeId = responseDto.getStoreId();
        OrderState updatedOrderState = responseDto.getUpdatedOrderState();
        LocalDateTime now = LocalDateTime.now();

        log.info("Order ID: {}", orderId);
        log.info("Store ID: {}", storeId);
        log.info("Updated Order State: {}", updatedOrderState);
        log.info("Updated Date and Time: {}", now);

        return result;
    }
}