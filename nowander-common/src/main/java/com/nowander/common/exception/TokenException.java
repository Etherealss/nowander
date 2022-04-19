package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;
import org.springframework.security.core.AuthenticationException;

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
