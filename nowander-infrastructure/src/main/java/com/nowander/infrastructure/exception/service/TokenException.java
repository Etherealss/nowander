package com.nowander.infrastructure.exception.service;


import com.nowander.infrastructure.exception.ApiInfoGetter;
import com.nowander.infrastructure.exception.BaseException;

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
