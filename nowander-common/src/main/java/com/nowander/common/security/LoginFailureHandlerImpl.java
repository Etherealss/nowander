package com.nowander.common.security;


import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.CaptchaException;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description 登录失败处理器
 * @date 2021-10-05
 */
@Slf4j
@Component
public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException {
        if (e instanceof CaptchaException) {
            handleCaptchaException(request, response, (CaptchaException) e);
        } else if (e instanceof BadCredentialsException) {
            handleBadCredentialsException(request, response, (BadCredentialsException) e);
        } else {
            ResponseUtil.send(response, Msg.exception(e));
            log.warn("登录失败:" + e.getMessage());
        }
    }

    /**
     * 验证码异常导致登录失败
     */
    public void handleCaptchaException(HttpServletRequest request,
                                       HttpServletResponse response,
                                       CaptchaException captchaException) throws IOException {
        Msg<?> msg = new Msg<>();
        msg.setCode(captchaException.getCode());
        msg.setMessage(captchaException.getMsg());
        response.setStatus(200);
        ResponseUtil.send(response, msg);
        log.trace("登录失败:" + captchaException.getMsg() + captchaException.getMessage());
    }

    /**
     * 密码错误导致登录失败
     */
    public void handleBadCredentialsException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              BadCredentialsException e) throws IOException {
        Msg<?> msg = new Msg<>(ApiInfo.LOGIN_FAIL, e.getMessage());
        ResponseUtil.send(response, msg);
        log.trace("登录失败：{}", e.getMessage());
    }

}
