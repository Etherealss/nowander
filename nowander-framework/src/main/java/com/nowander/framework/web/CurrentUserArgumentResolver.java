package com.nowander.framework.web;

import com.nowander.common.annotation.NoCurrentUserParam;
import com.nowander.common.pojo.po.User;
import com.nowander.common.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义参数解析器，通过获取Request中的JWT数据，包装为User对象
 * @author wtk
 * @date 2022-01-26
 */
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断当前参数是否为User
        return parameter.getParameterType() == User.class
                // 没有添加 NoCurrentUserParam 注解
                && !parameter.hasParameterAnnotation(NoCurrentUserParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 通过webRequest获取HttpServletRequest，从中通过Header获取Token，封装到User中
        return TokenUtil.getUserByToken(webRequest.getNativeRequest(HttpServletRequest.class));
    }
}
