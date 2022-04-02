package com.nowander.common.web;

import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.*;
import com.nowander.common.pojo.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.UnsupportedOperationException;

/**
 * @author wtk
 * @version 2.0 将多个异常合并
 * @description 全局异常处理
 * @date 2021-08-13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            UnsupportedOperationException.class,
            ErrorParamException.class,
            MismatchException.class,
            MissingParamException.class
    })
    public Msg<Object> handle(BaseException e) {
        log.info("Bad Request异常：" + e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 成功响应，结果不符合预期
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            SimpleException.class,
            ExistException.class,
            NotFoundException.class,
            CaptchaException.class
    })
    public Msg<Object> handleOk(BaseException e) {
        return new Msg<>(e);
    }


    /**
     * 前后端交接的接口参数缺失
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Msg<Object> handle(MissingServletRequestParameterException e) {
        log.warn("前后端交接的接口参数缺失:" + e.getMessage());
        return new Msg<>(ApiInfo.ERROR_PARAM, "前后端交接的接口参数缺失引起MissingServletRequestParameterException异常：" + e.getMessage());
    }

    /**
     * 参数不可读
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Msg<Object> handle(HttpMessageNotReadableException e) {
        log.warn("参数不可读异常：" + e.getMessage());
        return new Msg<>(ApiInfo.ERROR_PARAM, "参数不可读，请检查参数列表是否完整：" + e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    public Msg<Object> handle(HttpRequestMethodNotSupportedException e) {
        return new Msg<>(ApiInfo.OPERATE_UNSUPPORTED);
    }

    /**
     * 无权访问
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Msg<Object> handle(AccessDeniedException e) {
        log.info("[无权访问]" + e.getMessage());
        return new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
    }

    /**
     * 其他未处理异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Msg<Object> handle(Exception e) {
        if (e instanceof IllegalStateException) {
            if (e.getMessage().contains("argument type mismatch\nController")) {
                log.warn("参数类型错误:", e);
                return new Msg<>(new ErrorParamException("参数类型错误"));
            }
        }
        log.warn("[全局异常处理器]其他异常:", e);
        return new Msg<>(e);
    }
}
