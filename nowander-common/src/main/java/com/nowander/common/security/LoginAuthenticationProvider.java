package com.nowander.common.security;

import cn.hutool.core.util.StrUtil;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.CaptchaException;
import com.nowander.common.pojo.po.User;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 重写 AbstractUserDetailsAuthenticationProvider，它的默认实现是 DaoAuthenticationProvider
 * 我们要仿照 DaoAuthenticationProvider，实现新功能，在登录是校验验证码
 * @author wang tengkun
 * @date 2022/2/23
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */
@Component
@AllArgsConstructor
public class LoginAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;
    private RedisTemplate<String, String> redisTemplate;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginWebAuthenticationDetails loginWebAuthenticationDetails =
                (LoginWebAuthenticationDetails) authentication.getDetails();
        //验证码value
        String captchaCode = loginWebAuthenticationDetails.getCaptchaCode();
        //验证码key
        String captchaKey = loginWebAuthenticationDetails.getCaptchaKey();
        //检验验证码是否正确
        validateCaptcha(captchaCode, captchaKey);

        //用户名
        String username = authentication.getName();
        //密码
        String password = authentication.getCredentials().toString();
        User user = (User) userDetailsService.loadUserByUsername(username);
        //密码是否一致
        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        return this.createSuccessAuthentication(user, authentication, user);
    }

    private void validateCaptcha(String userInputCaptcha, String cacheKey) {
        String code = redisTemplate.opsForValue().getAndDelete(cacheKey);

        //验证码是否为空
        if (StrUtil.isBlank(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_MISSING);
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
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return null;
    }
}
