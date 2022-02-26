package com.nowander.common.interceptor.pathvariable;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 各个变量的作用见 {@link VersifyPathVariable}
 * @see PathVariableVersifyMappingConfig
 * @see VersifyPathVariable
 * @author wang tengkun
 * @date 2022/2/25
 */
@Data
public class PathVariableVersifyBean {
    private String pathVariableName;
    public Function<String, ?> map;
    private String beanName;
    private String methodName;
    private Object bean;
    private Method method;
    private Class<?>[] argsType;
}
