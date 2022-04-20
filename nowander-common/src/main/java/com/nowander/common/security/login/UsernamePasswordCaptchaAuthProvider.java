package com.nowander.common.security.login;

import cn.hutool.core.util.StrUtil;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.enums.AppAttribute;
import com.nowander.common.exception.CaptchaException;
import com.nowander.common.pojo.po.User;
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
        //检验验证码是否正确
        validateCaptcha(captchaCode);

        //用户名
        String username = auth.getUsername();
        //密码
        String password = auth.getPassword();
        User user = (User) userDetailsService.loadUserByUsername(username);
        //密码是否一致
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        return this.createSuccessAuthentication(user, auth, user);
    }

    /**
     * 认证成功将非授信凭据转为授信凭据.
     * 封装用户信息 角色信息。
     *
     * @param authentication the authentication
     * @param user           the user
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
                authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        log.debug("User已鉴权");
        return result;
    }

    private void validateCaptcha(String userInputCaptcha) {
        String code = redisTemplate.opsForValue().getAndDelete(AppAttribute.CAPTCHAC_CACHE);

        //验证码是否为空
        if (StrUtil.isBlank(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_MISSING);
        }

        // TODO 用于测试
        if ("1234".equals(userInputCaptcha)) {
            return;
        }

        // 验证码失效
        if (code == null) {
            throw new CaptchaException(ApiInfo.CAPTCHA_INVALID);
        }

        // 验证码不匹配
        if (!code.equals(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_NOT_MATCH);
        }
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
