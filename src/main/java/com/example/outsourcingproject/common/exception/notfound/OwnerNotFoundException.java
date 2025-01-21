package com.example.outsourcingproject.common.exception.notfound;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class OwnerNotFoundException extends NotFoundException {

    public OwnerNotFoundException() {
        super(ErrorCode.NOT_FOUND_OWNER);
    }
}