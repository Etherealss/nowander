package com.nowander.basesystem.user.security.login;

import com.nowander.infrastructure.exception.MissingParamException;
import com.nowander.infrastructure.exception.UnsupportedOperationException;
import com.nowander.basesystem.user.security.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    private final LoginFailureHandler loginFailureHandler;

    public LoginAuthenticationFilter(LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
        super(new AntPathRequestMatcher(SecurityConfig.LOGIN_MAPPING_URL, HttpMethod.POST.name()));
        this.loginFailureHandler = loginFailureHandler;
        this.setAuthenticationFailureHandler(loginFailureHandler);
        this.setAuthenticationSuccessHandler(loginSuccessHandler);
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
                throw new MissingParamException("登录方式参数'" + LoginParamName.LOGIN_TYPE + "'缺失");
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
        } catch (Exception e) {
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }
        return null;
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }




}
