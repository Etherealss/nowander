package com.nowander.infrastructure.exception;

/**
 * @author wang tengkun
 * @date 2022/3/18
 */
public interface ApiExceptionThrower extends ApiInfoGetter {
    default BaseException newException(String message) {
        return new BaseException(this, message);
    }

    default BaseException newException(String message, Throwable t) {
        return new BaseException(this, message, t);
    }
}
