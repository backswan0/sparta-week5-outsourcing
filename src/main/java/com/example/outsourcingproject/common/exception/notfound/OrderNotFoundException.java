package com.example.outsourcingproject.common.exception.notfound;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class OrderNotFoundException extends NotFoundException {

    public OrderNotFoundException() {
        super(ErrorCode.NOT_FOUND_ORDER);
    }
}
