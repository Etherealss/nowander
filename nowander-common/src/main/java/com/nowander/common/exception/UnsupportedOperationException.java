package com.nowander.common.exception;


import com.wanderfour.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class UnsupportedOperationException extends ServiceException {
    public UnsupportedOperationException() {
        super(ApiInfo.REQUEST_UNSUPPORTED);
    }

    public UnsupportedOperationException(String message) {
        super(ApiInfo.REQUEST_UNSUPPORTED, message);
    }
}
