package com.nowander.framework.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.framework.annotation.ResponseAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 方法返回 Msg 实例时，照常返回
 * 方法返回为空时，返回 Msg.ok()
 * 方法返回对象时，包装在 Msg 中返回
 * @author wtk
 * @date 2022/2/21
 */
@Slf4j
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 方法上 或者所在的类上 有 ResponseAdvice 注解，返回true
        return returnType.getMethodAnnotation(ResponseAdvice.class) != null
            || returnType.getDeclaringClass().isAnnotationPresent(ResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.debug("object:{}", o);
        log.debug("returnType:{}", returnType);
        if (o == null) {
            return Msg.ok();
        }
        if (o instanceof Msg) {
            return o;
        }
        return Msg.ok(o);
    }



}
