package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;
import org.springframework.security.core.AuthenticationException;

/**
 * @author wtk
 * @description 验证码异常
 * @date 2021-10-05
 */
public class CaptchaException extends BaseException {
    public CaptchaException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public CaptchaException(ApiInfo apiInfo) {
        super(apiInfo);
    }
}
