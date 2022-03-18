package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class NotFoundException extends BaseException {
    public NotFoundException() {
        super(ApiInfo.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(ApiInfo.NOT_FOUND, message);
    }
}
