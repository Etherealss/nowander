package com.nowander.common.pojo.vo;

import com.wanderfour.nowander.common.enums.ApiInfo;
import com.wanderfour.nowander.common.exception.ServiceException;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.AuthenticationException;

import java.io.Serializable;

/**
 * @author wtk
 * @description 统一的接口信息包，用于前后端交互
 * @date 2021-08-12
 */
@Getter
@ToString
public class Msg<T> implements Serializable {
    /** 结果码，用于判断响应情况 */
    private int code;

    /** 响应情况说明，如“用户密码错误” */
    private String message;

    /** 响应的数据，可能为空 */
    private T data;

    public static <T> Msg<T> ok() {
        return new Msg<>(ApiInfo.OK);
    }

    public static <T> Msg<T> ok(T data) {
        return new Msg<T>(ApiInfo.OK).setData(data);
    }

    public static <T> Msg<T> ok(String message) {
        return new Msg<>(ApiInfo.OK, message);
    }

    public static <T> Msg<T> userUnlogged() {
        return new Msg<>(ApiInfo.UNAUTHORIZED);
    }

    public static <T> Msg<T> exception() {
        return new Msg<>();
    }

    public static <T> Msg<T> exception(String message) {
        return new Msg<>(ApiInfo.SERVER_ERROR, message);
    }

    public static <T> Msg<T> exception(Throwable e) {
        return new Msg<>(ApiInfo.SERVER_ERROR, e.getMessage());
    }

    public static <T> Msg<T> notFound(String message) {
        return new Msg<>(ApiInfo.NOT_FOUND, message);
    }

    public static <T> Msg<T> paramsError(String message) {
        return new Msg<>(ApiInfo.ERROR_PARAM, message);
    }

    public static <T> Msg<T> authFail(AuthenticationException e) {
        return new Msg<T>(ApiInfo.AUTHORIZATION_FAILED, e);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Msg<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Msg<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 默认访问成功 OK
     */
    public Msg() {
        this(ApiInfo.OK);
    }

    /**
     * 默认访问成功 OK
     */
    public Msg(T data) {
        this(ApiInfo.OK);
        this.data = data;
    }

    public Msg(ApiInfo apiInfo) {
        this.code = apiInfo.getCode();
        this.message = apiInfo.getMessage();
    }


    public Msg(ApiInfo apiInfo, String description) {
        this.code = apiInfo.getCode();
        this.message = apiInfo.getMessage() + ": " + description;
    }

    public Msg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 用自定义的异常生成ApiMsg
     * 自定义异常的message包含了ApiInfo的说明和自定义描述
     * @param e
     */
    public Msg(ServiceException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public Msg(ApiInfo apiInfo, Throwable throwable) {
        this.code = apiInfo.getCode();
        String message = apiInfo.getMessage();
        if (throwable.getMessage().length() > 0) {
            message += (": " + throwable.getMessage());
        }
        this.message = message;
    }

    /**
     * 使用非自定义的其他异常生成ApiMsg
     * 手动拼接ApiInfo的说明和自定义描述
     * @param throwable
     */
    public Msg(Throwable throwable) {
        this.code = ApiInfo.SERVER_ERROR.getCode();
        String message = ApiInfo.SERVER_ERROR.getMessage();
        if (throwable.getMessage().length() > 0) {
            message += (": " + throwable.getMessage());
        }
        this.message = message;
    }
}
