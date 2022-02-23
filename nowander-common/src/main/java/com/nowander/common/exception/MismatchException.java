package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description 信息不匹配
 * @date 2021-08-12
 */
public class MismatchException extends AbstractServiceException {
    public MismatchException() {
        super(ApiInfo.MISMATCH);
    }

    public MismatchException(String message) {
        super(ApiInfo.MISMATCH, message);
    }
}
