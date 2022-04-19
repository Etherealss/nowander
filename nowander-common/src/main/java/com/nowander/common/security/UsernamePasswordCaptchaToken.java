package com.nowander.common.security;

import cn.hutool.core.lang.Assert;
import com.nowander.common.security.login.LoginParamName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 模仿UsernamePasswordAuthenticationToken
 * @author wang tengkun
 * @date 2022/4/19
 */
@Getter
@Setter
@ToString
public class UsernamePasswordCaptchaToken extends AbstractAuthenticationToken {

    /**
     * 用户名
     */
    private final String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private final String captchaCode;

    public UsernamePasswordCaptchaToken(HttpServletRequest request) {
        super(null);
        this.captchaCode = request.getParameter(LoginParamName.CAPTCHA_CODE);
        this.username = request.getParameter(LoginParamName.USERNAME);
        this.password = request.getParameter(LoginParamName.USERNAME);
        Assert.notBlank(this.username, "用户名不能为空");
        Assert.notBlank(this.password, "密码不能为空");
        Assert.notBlank(this.captchaCode, "验证码不能为空");
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
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
        password = null;
    }


}