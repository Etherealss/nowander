package com.nowander.blog.filter;

import com.wanderfour.nowander.common.config.security.SecurityConfig;
import com.wanderfour.nowander.common.enums.ApiInfo;
import com.wanderfour.nowander.common.exception.CaptchaException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description 登录验证码检查过滤器
 * @date 2021-10-05
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginCaptchaFilter extends OncePerRequestFilter {
    /**
     * 前端验证码参数名
     */
    private static final String LOGIN_CAPTCHAC_PARAM = "loginCaptcha";
    /**
     * 前端时间戳参数名
     */
    private static final String REDIS_CAPTCHAC_KEY = "captchaTimestamp";

    private AuthenticationFailureHandler failureHandler;
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 判断是否为登录请求
        boolean isLoginRequest = request.getRequestURI().equals(SecurityConfig.LOGIN_MAPPING_URL) &&
                "POST".equals(StringUtils.upperCase(request.getMethod()));

//        log.debug("【验证码检查过滤器】是否为登录请求：{}", isLoginRequest);

        //如果不是登录请求，直接调用后面的过滤器链
        if (!isLoginRequest) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            this.validate(request);
            filterChain.doFilter(request, response);
        } catch (CaptchaException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        }

    }

    private void validate(HttpServletRequest request) {
        String timestamp = request.getParameter(REDIS_CAPTCHAC_KEY);
        String code = redisTemplate.opsForValue().get(timestamp);

        // 验证码失效
        if (code == null) {
            throw new CaptchaException(ApiInfo.CAPTCHA_INVALID);
        }

        String userInputCaptcha = request.getParameter(LOGIN_CAPTCHAC_PARAM);
        // 未输入验证码
        if (userInputCaptcha == null) {
            throw new CaptchaException(ApiInfo.CAPTCHA_MISSING);
        }
        // 验证码不匹配
        if (!code.equals(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_NOT_MATCH);
        }
    }
}
