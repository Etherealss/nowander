package com.nowander.basesystem.user.security;

import org.springframework.security.config.annotation.web.builders.WebSecurity;

import java.lang.annotation.*;

/**
 * @see SecurityConfig#configure(WebSecurity)
 * @author wang tengkun
 * @date 2022/4/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
