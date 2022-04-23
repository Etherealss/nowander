package com.nowander.basesystem.user.security.login;

import com.nowander.basesystem.captcha.CaptchaService;
import com.nowander.basesystem.user.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * 重写 AbstractUserDetailsAuthenticationProvider，它的默认实现是 DaoAuthenticationProvider
 * 我们要仿照 DaoAuthenticationProvider，实现新功能，在登录时校验验证码
 * @author wang tengkun
 * @date 2022/2/23
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 */
@Slf4j
@RequiredArgsConstructor
public class UsernamePasswordCaptchaAuthProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private final UserDetailsService userDetailsService;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final CaptchaService captchaService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordCaptchaToken.class, authentication,
                () -> messages.getMessage(
                        "CaptchaAuthenticationProvider.onlySupports",
                        "Only CaptchaAuthenticationToken is supported"));
        UsernamePasswordCaptchaToken auth =
                (UsernamePasswordCaptchaToken) authentication;

        //验证码value
        String captchaCode = auth.getCaptchaCode();
        Date captchaCacheTimestamp = auth.getCaptchaCacheTimestamp();
        //检验验证码是否正确
        captchaService.validateCaptcha(captchaCode, captchaCacheTimestamp);

        //用户名
        String username = auth.getUsername();
        //密码
        String password = auth.getPassword();
        SysUser sysUser = (SysUser) userDetailsService.loadUserByUsername(username);
        //密码是否一致
        if (!passwordEncoder.matches(password, sysUser.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        return this.createSuccessAuthentication(sysUser, auth, sysUser);
    }

    /**
     * 认证成功将非授信凭据转为授信凭据.
     * 封装用户信息 角色信息。
     * @param authentication the authentication
     * @param user the user
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
                authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordCaptchaToken.class.isAssignableFrom(authentication));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userDetailsService, "userDetailsService must not be null");
        Assert.notNull(redisTemplate, "redisTemplate must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
