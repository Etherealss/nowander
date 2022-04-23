package com.nowander.basesystem.user.security;

import org.springframework.security.config.annotation.web.builders.WebSecurity;

import java.lang.annotation.*;

/**
 * 用于标识公共接口，不必登录，不必鉴权
 * @see SecurityConfig#configure(WebSecurity)
 * @author wang tengkun
 * @date 2022/4/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLogin {
}
