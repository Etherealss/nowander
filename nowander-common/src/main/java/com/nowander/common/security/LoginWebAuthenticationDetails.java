package com.nowander.common.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 保存登录验证码
 * @see LoginAuthenticationProvider
 * @author wang tengkun
 * @date 2022/2/23
 */
@Getter
@ToString
public class LoginWebAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * 前端验证码参数名
     */
    private static final String LOGIN_CAPTCHAC_PARAM_NAME = "loginCaptcha";
    /**
     * 前端时间戳参数名
     */
    private static final String LOGIN_CAPTCHA_KEY_NAME = "captchaTimestamp";

    private final String captchaCode;

    private final String captchaKey;

    public LoginWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.captchaCode = request.getParameter(LOGIN_CAPTCHAC_PARAM_NAME);
        this.captchaKey = request.getParameter(LOGIN_CAPTCHA_KEY_NAME);
    }
}
