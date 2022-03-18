package com.nowander.common.enums;

import com.nowander.common.exception.ApiExceptionThrower;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum ApiInfo implements ApiExceptionThrower {

    OK(HttpStatus.OK, 200000, "OK"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400000, "请求报文语法错误[参数校验失败]"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401000, "[未认证身份]"),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, 403000, "[没有权限]"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404000, "[资源不存在]"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500000, "[服务运行异常]"),
    SERVER_BUSY(HttpStatus.INTERNAL_SERVER_ERROR, 500000, "[服务繁忙]"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503000, "[服务不可用]"),

    MISSING_PARAM(HttpStatus.BAD_REQUEST, 400001, "[参数缺失]"),
    ERROR_PARAM(HttpStatus.BAD_REQUEST, 400002, "[参数不合法]"),
    EXIST(HttpStatus.OK, 400003, "[目标已存在]"),
    MISMATCH(HttpStatus.BAD_REQUEST, 400004, "[信息不匹配]"),
    OPERATE_UNSUPPORTED(HttpStatus.BAD_REQUEST, 400005, "[请求不支持]"),
    NOT_AUTHOR(HttpStatus.OK, 400006, "[不是作者]"),

    AUTHORIZATION_FAILED(HttpStatus.FORBIDDEN,400011, "[认证未通过]"),

    LOGIN_FAIL(HttpStatus.OK, 400201, "[登录失败]"),
    PASSWORD_ERROR(HttpStatus.OK, 400202, "[密码错误]"),
    LOGIN_USER_NOT_FOUND(HttpStatus.OK, 400203, "[登录用户不存在]"),
    USER_LOGGED(HttpStatus.OK, 400204, "[用户已登录]"),

    CAPTCHA_MISSING(HttpStatus.OK, 400301, "[未输入验证码]"),
    CAPTCHA_NOT_MATCH(HttpStatus.OK, 400302, "[验证码不匹配]"),
    CAPTCHA_INVALID(HttpStatus.OK, 400303, "[验证码已失效]"),
    CAPTCHA_ERROR(HttpStatus.OK, 400304, "[验证码异常]"),

    TOKEN_MISSING(HttpStatus.OK, 400401, "[token缺失]"),
    TOKEN_INVALID(HttpStatus.OK, 400402, "[token无效]"),
    TOKEN_EXP(HttpStatus.OK, 400403, "[token已过期]"),

    LIKE_DUPLICATE(HttpStatus.OK, 4000501, "重复点赞或取消");

    ;

    final HttpStatus httpStatus;
    final int code;
    final String message;

    @Override
    public String toString() {
        return super.toString();
    }
}
