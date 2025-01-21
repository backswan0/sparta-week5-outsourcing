package com.example.outsourcingproject.common.exception.invalidtransition;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class InvalidTransitionFromPendingException extends InvalidTransitionException {

    public InvalidTransitionFromPendingException() {
        super(ErrorCode.INVALID_PENDING_STATE_TRANSITION);
    }
}