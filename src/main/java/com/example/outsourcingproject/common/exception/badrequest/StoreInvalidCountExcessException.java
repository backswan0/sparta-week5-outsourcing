package com.example.outsourcingproject.common.exception.badrequest;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class StoreInvalidCountExcessException extends BadRequestException {

    public StoreInvalidCountExcessException() {
        super(ErrorCode.BAD_REQUEST_STORE_LIMIT);
    }
}
