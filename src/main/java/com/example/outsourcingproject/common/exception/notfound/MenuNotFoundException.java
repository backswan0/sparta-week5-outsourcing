package com.example.outsourcingproject.common.exception.notfound;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class MenuNotFoundException extends NotFoundException {

    public MenuNotFoundException() {
        super(ErrorCode.NOT_FOUND_MENU);
    }
}
