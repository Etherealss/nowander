package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class ExistException extends BaseException {
    public ExistException() {
        super(ApiInfo.EXIST);
    }

    public ExistException(String message) {
        super(ApiInfo.EXIST, message);
    }
}
