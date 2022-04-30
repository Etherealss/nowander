package com.nowander.infrastructure.exception.internal;

import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.BaseException;

/**
 * 有bug
 * @author wtk
 * @date 2022-04-25
 */
public class BugException extends BaseException {
    public BugException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }
}
