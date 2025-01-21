package com.example.outsourcingproject.common.exception.invalidtransition;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class InvalidTransitionFromAcceptedException extends InvalidTransitionException {

    public InvalidTransitionFromAcceptedException() {
        super(ErrorCode.INVALID_ACCEPTED_STATE_TRANSITION);
    }
}
