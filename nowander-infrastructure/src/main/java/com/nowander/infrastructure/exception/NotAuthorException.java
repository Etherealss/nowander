package com.nowander.infrastructure.exception;

import com.nowander.infrastructure.enums.ApiInfo;

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
