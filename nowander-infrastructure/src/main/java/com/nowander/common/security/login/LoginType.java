package com.nowander.common.security.login;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang tengkun
 * @date 2022/4/19
 */
public enum LoginType {
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

    String name;
    String paramName;
    Integer code;

    public String getName() {
        return name;
    }

    public String getParamName() {
        return paramName;
    }

    public Integer getCode() {
        return code;
    }

    LoginType(String name, Integer code) {
        this(name, name, code);
    }

    LoginType(String name, String paramName, Integer code) {
        this.name = name;
        this.paramName = paramName;
        this.code = code;
    }
}
