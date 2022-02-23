package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class MissingParamException extends AbstractServiceException {
    public MissingParamException() {
        super(ApiInfo.MISSING_PARAM);
    }

    public MissingParamException(String message) {
        super(ApiInfo.MISSING_PARAM, message);
    }
}
