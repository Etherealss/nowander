package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class ErrorParamException extends AbstractServiceException {
    public ErrorParamException() {
        super(ApiInfo.ERROR_PARAM);
    }

    public ErrorParamException(String message) {
        super(ApiInfo.ERROR_PARAM, message);
    }
}
