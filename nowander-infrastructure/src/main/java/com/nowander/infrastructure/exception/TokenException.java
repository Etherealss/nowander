package com.nowander.infrastructure.exception;


/**
 * @author wtk
 * @description token异常
 * @date 2021-10-05
 */
public class TokenException extends BaseException {
    public TokenException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public TokenException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }
}
