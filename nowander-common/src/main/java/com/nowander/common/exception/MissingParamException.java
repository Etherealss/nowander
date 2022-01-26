package com.nowander.common.exception;

import com.wanderfour.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class MissingParamException extends ServiceException {
    public MissingParamException() {
        super(ApiInfo.MISSING_PARAM);
    }

    public MissingParamException(String message) {
        super(ApiInfo.MISSING_PARAM, message);
    }
}
