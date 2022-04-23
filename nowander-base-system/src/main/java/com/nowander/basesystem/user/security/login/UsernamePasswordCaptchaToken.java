package com.nowander.basesystem.user.security.login;

import cn.hutool.core.util.StrUtil;
import com.nowander.infrastructure.exception.MissingParamException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.nowander.basesystem.user.security.login.LoginParamName.*;

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

    /**
     * 用于从缓存获取验证码
     */
    private final Date captchaCacheTimestamp;

    public UsernamePasswordCaptchaToken(HttpServletRequest request) {
        super(null);
        this.captchaCode = request.getParameter(CAPTCHA_CODE);
        this.username = request.getParameter(USERNAME);
        this.password = request.getParameter(PASSWORD);
        String timestampStr = request.getParameter(CAPTCHA_CACHE_TIMESTAMP);

        notBlank(this.username, "用户名参数'" + USERNAME + "'不能为空");
        notBlank(this.password, "密码参数'" + PASSWORD + "'不能为空");
        notBlank(this.captchaCode, "验证码参数'" + CAPTCHA_CODE + "'不能为空");
        notBlank(timestampStr, "验证码时间戳参数'" + CAPTCHA_CACHE_TIMESTAMP + "'不能为空");

        this.captchaCacheTimestamp = new Date(Long.parseLong(timestampStr));
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