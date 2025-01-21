package com.example.outsourcingproject.common.exception.invalidtransition;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class InvalidTransitionFromDeliveringException extends InvalidTransitionException {

    public InvalidTransitionFromDeliveringException() {
        super(ErrorCode.INVALID_DELIVERING_STATE_TRANSITION);
    }
}
