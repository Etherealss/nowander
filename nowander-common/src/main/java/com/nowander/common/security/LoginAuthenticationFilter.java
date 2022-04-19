package com.nowander.common.security;

import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;
import com.nowander.common.exception.CaptchaException;
import com.nowander.common.exception.MissingParamException;
import com.nowander.common.exception.UnsupportedOperationException;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.security.login.LoginParamName;
import com.nowander.common.security.login.LoginType;
import com.nowander.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重写UsernamePasssowrdAuthenticationFilter
 * @author wang tengkun
 * @date 2022/4/19
 */
@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public LoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConfig.LOGIN_MAPPING_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        try {
            AbstractAuthenticationToken authRequest;
            String loginType = request.getParameter(LoginParamName.LOGIN_TYPE);
            if (loginType == null || loginType.isEmpty()) {
                throw new MissingParamException("登录方式参数缺失");
            }
            if (LoginType.USERNAME.getParamName().equals(loginType)) {
                authRequest = new UsernamePasswordCaptchaToken(request);
            } else if (LoginType.PHONE.getParamName().equals(loginType)) {
                authRequest = new PhoneCaptchaToken(request);
            } else {
                throw new UnsupportedOperationException("不支持的登录方式: '" + loginType + "'");
            }

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (BadCredentialsException e) {
            handleBadCredentialsException(request, response, e);
        } catch (CaptchaException e) {
            handleCaptchaException(request, response, e);
        } catch (BaseException e) {
            handleBaseException(request, response, e);
        } catch (Exception e) {
            handleOtherException(request, response, e);
        }
        return null;
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
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

    /**
     * 验证码异常导致登录失败
     */
    private void handleCaptchaException(HttpServletRequest request,
                                       HttpServletResponse response,
                                       CaptchaException captchaException) throws IOException {
        Msg<?> msg = new Msg<>();
        msg.setCode(captchaException.getCode());
        msg.setMessage(captchaException.getMsg());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ResponseUtil.send(response, msg);
        log.trace("登录失败:" + captchaException.getMsg() + captchaException.getMessage());
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
}
