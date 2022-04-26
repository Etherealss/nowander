package com.nowander.infrastructure.exception.rest;


import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class UnsupportedOperationException extends BaseException {
    public UnsupportedOperationException() {
        super(ApiInfo.OPERATE_UNSUPPORTED);
    }

    public UnsupportedOperationException(String message) {
        super(ApiInfo.OPERATE_UNSUPPORTED, message);
    }
}
