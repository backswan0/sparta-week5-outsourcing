package com.example.outsourcingproject.common.exception.notfound;

import com.example.outsourcingproject.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
