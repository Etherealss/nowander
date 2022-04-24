package com.nowander.basesystem.user.security.login;

import com.nowander.infrastructure.pojo.BaseEnum;

/**
 * @author wang tengkun
 * @date 2022/4/19
 */
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

    String paramName;
    Integer code;

    public String getParamName() {
        return paramName;
    }

    LoginType(String paramName, Integer code) {
        this.paramName = paramName;
        this.code = code;
    }

    @Override
    public String getName() {
        return paramName;
    }

    @Override
    public int getCode() {
        return code;
    }
}
