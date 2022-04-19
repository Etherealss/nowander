package com.nowander.common.security;

import cn.hutool.core.lang.Assert;
import com.nowander.common.security.login.LoginParamName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * 模仿UsernamePasswordAuthenticationToken
 * @author wang tengkun
 * @date 2022/4/19
 */
@Getter
@Setter
@ToString
public class PhoneCaptchaToken extends AbstractAuthenticationToken {

    /**
     * 手机
     */
    private final String phone;
    /**
     * 验证码
     */
    private String captchaCode;

    public PhoneCaptchaToken(HttpServletRequest request) {
        super(null);
        this.phone = request.getParameter(LoginParamName.PHONE);
        this.captchaCode = request.getParameter(LoginParamName.CAPTCHA_CODE);
        Assert.notBlank(this.phone, "手机号不能为空");
        Assert.notBlank(this.captchaCode, "验证码不能为空");
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.phone;
    }

    @Override
    public Object getPrincipal() {
        return this.captchaCode;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        captchaCode = null;
    }
}