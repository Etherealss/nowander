package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class UnsupportedOperationException extends AbstractServiceException {
    public UnsupportedOperationException() {
        super(ApiInfo.OPERATE_UNSUPPORTED);
    }

    public UnsupportedOperationException(String message) {
        super(ApiInfo.OPERATE_UNSUPPORTED, message);
    }
}
