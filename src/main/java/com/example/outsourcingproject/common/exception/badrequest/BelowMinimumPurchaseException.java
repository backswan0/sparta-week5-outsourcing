package com.example.outsourcingproject.common.exception.badrequest;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class BelowMinimumPurchaseException extends BadRequestException {

    public BelowMinimumPurchaseException() {
        super(ErrorCode.BAD_REQUEST_BELOW_MINIMUM_PURCHASE);
    }
}
