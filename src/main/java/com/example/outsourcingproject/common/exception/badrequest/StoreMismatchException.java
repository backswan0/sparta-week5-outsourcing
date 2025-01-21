package com.example.outsourcingproject.common.exception.badrequest;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class StoreMismatchException extends BadRequestException {

    public StoreMismatchException() {
        super(ErrorCode.BAD_REQUEST_STORE_MISMATCH);
    }
}
