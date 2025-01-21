package com.example.outsourcingproject.common.exception.notfound;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class StoreNotFoundException extends NotFoundException {

    public StoreNotFoundException() {
        super(ErrorCode.NOT_FOUND_STORE);
    }
}
