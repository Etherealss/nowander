package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;
import org.springframework.security.core.AuthenticationException;

/**
 * @author wtk
 * @description token异常
 * @date 2021-10-05
 */
public class TokenException extends AuthenticationException {

    private int code;
    private String msg;

    public TokenException(ApiInfo apiInfo, String message) {
        super(message + apiInfo.getMessage());
        this.code = apiInfo.getCode();
        this.msg = apiInfo.getMessage();
    }

    public TokenException(ApiInfo apiInfo) {
        super(apiInfo.getMessage());
        this.code = apiInfo.getCode();
        this.msg = apiInfo.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
