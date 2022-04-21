package com.nowander.common.security.login;


import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;
import com.nowander.common.exception.CaptchaException;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
public class LoginFailureHandler implements AuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Exception e) throws IOException {
        if (e instanceof BaseException) {
            handleBaseException(request, response, (BaseException) e);
        } else if (e instanceof BadCredentialsException) {
            handleBadCredentialsException(request, response, (BadCredentialsException) e);
        } else {
            handleOtherException(request, response, e);
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        throw new RuntimeException("不要用这个方法");
    }

    /**
     * 密码错误导致登录失败
     */
    private void handleBadCredentialsException(HttpServletRequest request,
                                               HttpServletResponse response,
                                               BadCredentialsException e) throws IOException {
        Msg<?> msg = new Msg<>(ApiInfo.PASSWORD_ERROR, e.getMessage());
        ResponseUtil.send(response, msg);
        log.trace("登录失败：{}", e.getMessage());
    }

    /**
     * 未知异常导致登录失败
     */
    private void handleOtherException(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Exception e) throws IOException {
        ResponseUtil.send(response, Msg.exception(e));
        log.warn("登录失败:" + e.getMessage());
    }

    /**
     * 未知异常导致登录失败
     */
    private void handleBaseException(HttpServletRequest request,
                                     HttpServletResponse response,
                                     BaseException e) throws IOException {
        Msg<Void> msg = new Msg<>();
        msg.setCode(e.getCode());
        msg.setMessage(e.getMessage());
        ResponseUtil.send(response, msg);
        log.warn("登录失败:" + e.getMessage());
    }

}
