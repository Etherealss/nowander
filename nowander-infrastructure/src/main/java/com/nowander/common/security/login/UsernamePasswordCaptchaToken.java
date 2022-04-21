package com.nowander.common.security.login;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.nowander.common.exception.MissingParamException;
import com.nowander.common.security.login.LoginParamName;
import io.netty.util.internal.StringUtil;
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
        this.password = request.getParameter(LoginParamName.PASSWORD);
        notBlank(this.username, "用户名参数'" + LoginParamName.USERNAME + "'不能为空");
        notBlank(this.password, "密码参数'" + LoginParamName.PASSWORD + "'不能为空");
        notBlank(this.captchaCode, "验证码参数'" + LoginParamName.CAPTCHA_CODE + "'不能为空");
        this.setAuthenticated(false);
    }

    private void notBlank(String text, String errorMsg) {
        if (StrUtil.isBlank(text)) {
            throw new MissingParamException(errorMsg);
        }
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