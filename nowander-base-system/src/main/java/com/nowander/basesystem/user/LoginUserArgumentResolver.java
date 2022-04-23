package com.nowander.basesystem.user;

import com.nowander.basesystem.user.security.SecurityUtil;
import com.nowander.infrastructure.web.NotLoginUserParam;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义参数解析器，通过获取Request中的JWT数据，包装为User对象
 * @author wtk
 * @date 2022-01-26
 */
@Component
@AllArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private TokenService tokenService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断当前参数是否为User
        return parameter.getParameterType() == SysUser.class
                // 没有添加 NoCurrentUserParam 注解
                && !parameter.hasParameterAnnotation(NotLoginUserParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return SecurityUtil.getLoginUser();
    }
}
