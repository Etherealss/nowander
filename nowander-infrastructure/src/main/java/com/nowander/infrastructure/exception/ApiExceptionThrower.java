package com.nowander.infrastructure.exception;

/**
 * 没有用到
 * @author wang tengkun
 * @date 2022/3/18
 */
public interface ApiExceptionThrower extends ApiInfoGetter {
    default BaseException throwException(String message) {
        throw new BaseException(this, message);
    }

    default BaseException throwException(String message, Throwable t) {
        throw new BaseException(this, message, t);
    }
}

