package com.example.outsourcingproject.exception.badrequest;

import com.example.outsourcingproject.exception.ErrorCode;

public class CategoryCountExcessException extends BadRequestException {

    public CategoryCountExcessException() {
        super(ErrorCode.BAD_REQUEST_CATEGORY_LIMIT);
    }
}