package com.nowander.infrastructure.exception.service;

import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.BaseException;

/**
 * @author wtk
 * @date 2022-02-04
 */
public class NotAuthorException extends BaseException {
    public NotAuthorException() {
        super(ApiInfo.NOT_AUTHOR);
    }

    public NotAuthorException(String message) {
        super(ApiInfo.NOT_AUTHOR, message);
    }
}
