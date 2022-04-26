package com.nowander.infrastructure.exception.rest;


import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class ErrorParamException extends BaseException {
    public ErrorParamException() {
        super(ApiInfo.ERROR_PARAM);
    }

    public ErrorParamException(String message) {
        super(ApiInfo.ERROR_PARAM, message);
    }
}
