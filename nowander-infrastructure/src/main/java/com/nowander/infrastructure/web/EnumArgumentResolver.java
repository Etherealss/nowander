package com.nowander.infrastructure.web;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nowander.infrastructure.pojo.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 通过字符串参数解析 BaseEnum 的子类
 * @author wtk
 * @date 2022-01-27
 */
@Slf4j(topic = "other")
public class EnumArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 使用了PathVariable注解，并且为BaseEnum的子类
        return parameter.hasParameterAnnotation(PathVariable.class) &&
                Arrays.asList(parameter.getParameterType().getInterfaces()).contains(BaseEnum.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        // 获取路径参数
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        PathVariable annotation = parameter.getParameterAnnotation(PathVariable.class);
        assert annotation != null;
        String paramName = annotation.value();
        // 通过路径变量名获取参数值
        String paramValue = pathVariables.get(paramName);
        // 被注解的枚举变量的数据类型
        Class<? extends BaseEnum> parameterType = (Class<? extends BaseEnum>) parameter.getParameterType();
        return BaseEnum.fromName(parameterType, paramValue);
    }

}
