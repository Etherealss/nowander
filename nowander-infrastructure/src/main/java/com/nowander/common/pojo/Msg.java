package com.nowander.common.pojo;

import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wtk
 * @description 统一的接口信息包，用于前后端交互
 * @date 2021-08-12
 */
@Getter
@Setter
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
        Msg<T> msg = new Msg<>(ApiInfo.OK);
        msg.setData(data);
        return msg;
    }
    public static <T> Msg<T> ok(String message) {
        return new Msg<>(ApiInfo.OK, message);
    }

    public static <T> Msg<T> exception(Throwable e) {
        return new Msg<>(ApiInfo.SERVER_ERROR, e.getMessage());
    }

    /**
     * 默认访问成功 OK
     */
    public Msg() {
        this(ApiInfo.OK);
    }

    public Msg(ApiInfo apiInfo) {
        this.code = apiInfo.getCode();
        this.message = apiInfo.getMessage();
    }


    public Msg(ApiInfo apiInfo, String description) {
        this.code = apiInfo.getCode();
        this.message = apiInfo.getMessage() + " " + description;
    }
    /**
     * 用自定义的异常生成ApiMsg
     * 自定义异常的message包含了ApiInfo的说明和自定义描述
     * @param e
     */
    public Msg(BaseException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
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
