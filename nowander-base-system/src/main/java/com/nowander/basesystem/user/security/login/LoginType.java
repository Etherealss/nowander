package com.nowander.basesystem.user.security.login;

import com.nowander.common.pojo.BaseEnum;

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
    Integer value;

    public String getParamName() {
        return paramName;
    }

    public Integer getValue() {
        return value;
    }

    LoginType(String paramName, Integer value) {
        this.paramName = paramName;
        this.value = value;
    }

    @Override
    public String display() {
        return paramName;
    }

    @Override
    public int value() {
        return value;
    }
}
