package com.example.outsourcingproject.common.exception;

import com.example.outsourcingproject.common.exception.badrequest.BadRequestException;
import com.example.outsourcingproject.common.exception.invalidtransition.InvalidTransitionException;
import com.example.outsourcingproject.common.exception.notfound.NotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        return handleException(
            ex.getErrorCode().getCode(),
            ex.getErrorCode().getMessage(),
            ex.getErrorCode().getStatus()
        );
    }

    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTransitionException(
        InvalidTransitionException ex) {
        return handleException(
            ex.getErrorCode().getCode(),
            ex.getErrorCode().getMessage(),
            ex.getErrorCode().getStatus()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(
        BadRequestException ex) {
        return handleException(
            ex.getErrorCode().getCode(),
            ex.getErrorCode().getMessage(),
            ex.getErrorCode().getStatus()
        );
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handlerCustomException(CustomException ex) {
        Map<String, String> response = new LinkedHashMap<>();

        response.put("errorCode", ex.getErrorCode().getCode());
        response.put("errorMessage", ex.getErrorCode().getMessage());

        log.info("에러 발생 >>> 코드: {}, 메시지: {}",
            response.get("errorCode"), response.get("errorMessage"));
        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }

    private ResponseEntity<Map<String, String>> handleException(
        String errorCode,
        String errorMessage,
        HttpStatus status
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("errorCode", errorCode);
        response.put("errorMessage", errorMessage);

        log.info("에러 발생 >>> 코드: {}, 메시지: {}", errorCode, errorMessage);
        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
        MethodArgumentNotValidException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        List<String> errors = new ArrayList<>();

        // 검증 실패한 필드들을 에러 리스트에 추가
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });

        response.put("errorCode", "VALIDATION_ERROR");
        response.put("errorMessages", "형식에 맞지 않는 입력값입니다.");

        // 로깅
        log.info("검증 실패 >>> 오류 메시지: {}", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
