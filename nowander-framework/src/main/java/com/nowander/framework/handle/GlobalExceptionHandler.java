package com.nowander.framework.handle;

import com.wanderfour.nowander.common.enums.ApiInfo;
import com.wanderfour.nowander.common.exception.*;
import com.wanderfour.nowander.pojo.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
 * @description 全局异常处理
 * @date 2021-08-13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务错误
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SimpleException.class)
    public Msg<Object> handleSimpleServiceException(SimpleException e) {
        return new Msg<>(e.getCode(), e.getMessage());
    }

    /**
     * 不支持的操作
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedOperationException.class)
    public Msg<Object> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.info("[全局异常处理器]不支持的操作：" + e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 参数缺失
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingParamException.class)
    public Msg<Object> handleMissingParamException(MissingParamException e) {
        log.info("[全局异常处理器]参数缺失：" + e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 参数异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ErrorParamException.class)
    public Msg<Object> handleErrorParamException(ErrorParamException e) {
        log.info("[全局异常处理器]参数异常：" + e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 不匹配异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MismatchException.class)
    public Msg<Object> handleMismatchException(MismatchException e) {
        log.info("[全局异常处理器]不匹配异常：" + e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 已存在异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ExistException.class)
    public Msg<Object> handleExistException(ExistException e) {
        return new Msg<>(e);
    }

    /**
     * 不存在异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundException.class)
    public Msg<Object> handleNotFoundException(NotFoundException e) {
        return new Msg<>(e);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CaptchaException.class)
    public Msg<Object> handleCaptchaException(CaptchaException e) {
        return new Msg<>(e.getCode(), e.getMsg());
    }

    /**
     * MissingServletRequestParameterException 前后端交接的接口参数缺失
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Msg<Object> handleHttpMessageNotReadableException(MissingServletRequestParameterException e) {
        log.warn("前后端交接的接口参数缺失:" + e.getMessage());
        return Msg.paramsError("前后端交接的接口参数缺失引起MissingServletRequestParameterException异常：" + e.getMessage());
    }

    /**
     * 不支持的请求方式
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Msg<Object> handleNotFoundException(HttpRequestMethodNotSupportedException e) {
        log.warn("[全局异常处理器]不支持的请求方式：" + e.getMessage());
        return new Msg<>(ApiInfo.REQUEST_UNSUPPORTED);
    }

    /**
     * 方法不存在
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Msg<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("[全局异常处理器]方法不存在："+e.getMessage());
        return new Msg<>(ApiInfo.REQUEST_UNSUPPORTED);
    }

    /**
     * 参数缺失
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Msg<Object> handleAccessDeniedException(AccessDeniedException e) {
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
    public Msg<Object> handleException(Exception e) {
        log.warn("[全局异常处理器]其他异常：", e);
        return new Msg<>(e);
    }
}
