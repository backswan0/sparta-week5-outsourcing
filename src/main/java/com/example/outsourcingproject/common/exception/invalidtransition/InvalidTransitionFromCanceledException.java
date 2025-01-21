package com.example.outsourcingproject.common.exception.invalidtransition;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class InvalidTransitionFromCanceledException extends InvalidTransitionException {

    public InvalidTransitionFromCanceledException() {
        super(ErrorCode.INVALID_CANCELED_STATE_TRANSITION);
    }
}
