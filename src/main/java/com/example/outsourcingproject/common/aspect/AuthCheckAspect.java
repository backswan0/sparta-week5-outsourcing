package com.example.outsourcingproject.common.aspect;

import com.example.outsourcingproject.common.exception.CustomException;
import com.example.outsourcingproject.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckAspect {

    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(com.example.outsourcingproject.common.aspect.AuthCheck)")
    public Object authCheck(ProceedingJoinPoint joinPoint) throws Throwable {

        String authority = (String) httpServletRequest.getAttribute("authority");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AuthCheck authCheck = method.getAnnotation(AuthCheck.class);

        // 필요한 권한이 아닌 다른 권한으로 접근할 때
        if (authCheck != null && !authCheck.value().equals(authority)) {
            log.info("권한 없음 : 필요한 권한은 {}, 가지고 있는 권한 : {}", authCheck.value(), authority);
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return joinPoint.proceed();
    }

}
