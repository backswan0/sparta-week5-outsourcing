package com.example.outsourcingproject.common.exception.badrequest;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class InvalidOrderTimeException extends BadRequestException {

    public InvalidOrderTimeException() {
        super(ErrorCode.BAD_REQUEST_INVALID_ORDER_TIME);
    }
}
