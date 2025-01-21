package com.example.outsourcingproject.common.exception.badrequest;

import com.example.outsourcingproject.common.exception.ErrorCode;

public class CategoryCountExcessException extends BadRequestException {

    public CategoryCountExcessException() {
        super(ErrorCode.BAD_REQUEST_CATEGORY_LIMIT);
    }
}