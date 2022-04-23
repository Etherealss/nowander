package com.nowander.infrastructure.exception;


import com.nowander.infrastructure.enums.ApiInfo;

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
