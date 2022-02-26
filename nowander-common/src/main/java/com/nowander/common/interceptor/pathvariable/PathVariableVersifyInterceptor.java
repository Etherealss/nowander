package com.nowander.common.interceptor.pathvariable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import vip.maxhub.web.waterdrop.infrastructure.exception.ErrorCode;
import vip.maxhub.web.waterdrop.infrastructure.exception.RestException;
import vip.maxhub.web.waterdrop.infrastructure.utils.ReflectUtil;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wang tengkun
 * @date 2022/2/25
 */
@Component
@Slf4j
public class PathVariableVersifyInterceptor implements HandlerInterceptor {
    /**
     * key是@PathVariable("xxx")注解中的name值
     * value是用于反射确定路径参数的值是否存在的信息类
     */
    private final Map<String, PathVariableVersifyBean> VERSIFY_CACHE = new ConcurrentHashMap<>();

    private final Map<HandlerMethod, Set<String>> SKIP_PARAM_CACHE = new ConcurrentHashMap<>();

    /**
     * 单例的空Set
     */
    private static final Set<String> EMPTY = new HashSet<>(0);

    @Autowired
    public PathVariableVersifyInterceptor(List<PathVariableVersifyBean> list) {
        list.forEach(bean -> VERSIFY_CACHE.put(bean.getPathVariableName(), bean));
    }

    /**
     * @param request
     * @param response
     * @param handler 一般是HandlerMehtod（SpringMVC有好几种Handler）
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            // 不是HandlerMethod就跳过该拦截器
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        VersifyPathVariable classAnnotation = handlerMethod.getBeanType().getAnnotation(VersifyPathVariable.class);
        VersifyPathVariable methodAnnotation = method.getAnnotation(VersifyPathVariable.class);
        // 类和方法都没有注解，则跳过当前拦截器
        if (classAnnotation == null && methodAnnotation == null) {
            return true;
        }
        // 声明跳过当前类或方法
        if (classAnnotation != null && classAnnotation.skipCurrent()) {
            return true;
        }
        if (methodAnnotation != null && methodAnnotation.skipCurrent()) {
            return true;
        }

        // 获取要跳过校验的字段
        Set<String> skipParams = getSkip(handlerMethod);
        // 获取路径参数
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        pathVariables.forEach((paramName, value) -> {
            if (skipParams.contains(paramName)) {
                // 跳过
                return;
            }
            PathVariableVersifyBean versifyBean = getPathVariableVersifyBean(paramName, handlerMethod);
            Object result;
            try {
                result = ReflectUtil.invoke(versifyBean.getBeanName(), versifyBean.getMethodName(),
                        versifyBean.getArgsType(), versifyBean.getMap().apply(value));
            } catch (RestException e) {
                // 校验失败抛出的异常，再次抛出，给全局异常处理器
                throw e;
            } catch (Throwable throwable) {
                // 反射异常，包装一层异常再抛出，方便定位原因
                throw new RuntimeException("参数校验失败！原始异常信息：" + throwable.getMessage(), throwable);
            }
            // 没有异常，判断结果是否为null。如果是则说明查不到数据
            if (result == null) {
                throw new RestException(ErrorCode.PARAM_INVALID, paramName + "不存在");
            }
        });
        return true;
    }

    /**
     * 获取要跳过校验的参数
     * @param ha
     * @return
     */
    private Set<String> getSkip(HandlerMethod ha) {
        Set<String> strings = SKIP_PARAM_CACHE.get(ha);
        if (strings != null) {
            return strings;
        }
        VersifyPathVariable classAnnotation = ha.getBeanType().getAnnotation(VersifyPathVariable.class);
        Set<String> set = new CopyOnWriteArraySet<>();
        if (classAnnotation != null) {
            String[] classSkip = classAnnotation.skipParam();
            set.addAll(Arrays.stream(classSkip).collect(Collectors.toList()));
        }
        VersifyPathVariable methodAnnotation = ha.getMethod().getAnnotation(VersifyPathVariable.class);
        if (methodAnnotation != null) {
            String[] methodSkip = methodAnnotation.skipParam();
            set.addAll(Arrays.stream(methodSkip).collect(Collectors.toList()));
        }
        if (set.isEmpty()) {
            SKIP_PARAM_CACHE.put(ha, EMPTY);
        } else {
            SKIP_PARAM_CACHE.put(ha, set);
        }
        return set;
    }

    /**
     * 从注解中读取信息
     * @param paramName
     * @param handlerMethod
     * @return
     */
    @SuppressWarnings("unchecked")
    private PathVariableVersifyBean getPathVariableVersifyBean(String paramName, HandlerMethod handlerMethod) {
        PathVariableVersifyBean versifyBean = VERSIFY_CACHE.get(paramName);
        if (versifyBean != null) {
            return versifyBean;
        }
        // 从注解中获取数据
        VersifyPathVariable annotation = handlerMethod.getBeanType().getAnnotation(VersifyPathVariable.class);
        versifyBean = new PathVariableVersifyBean();
        versifyBean.setPathVariableName(paramName);
        versifyBean.setBeanName(annotation.beanName());
        versifyBean.setMethodName(annotation.methodName());
        Object o = ReflectUtil.newInstance(annotation.mapper(), new Class<?>[0], new Object[0]);
        versifyBean.setMap((Function<String, ?>) o);
        versifyBean.setArgsType(annotation.methodArgsType());

        // 检查是否完整
        boolean uncomplete = versifyBean.getBeanName() == null || versifyBean.getMethodName() == null || versifyBean.getMap() == null;
        if (uncomplete) {
            throw new RuntimeException("缺少校验'" + paramName + "'参数的bean");
        }

        VERSIFY_CACHE.put(paramName, versifyBean);

        return versifyBean;
    }
}
