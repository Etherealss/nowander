package com.nowander.infrastructure.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang tengkun
 * @date 2022/4/19
 */
@Getter
@AllArgsConstructor
public enum LoginType implements BaseEnum {
    /**
     * 用户名、密码、验证码登录
     */
    USERNAME("username", 0),
    /**
     * 手机验证码登录
     */
    PHONE("phone", 1),
    /**
     * 邮箱验证码登录
     */
    EMAIL("email", 2),
    ;

    private final String paramName;
    private final int code;

    @Override
    public String getName() {
        return paramName;
    }
}
