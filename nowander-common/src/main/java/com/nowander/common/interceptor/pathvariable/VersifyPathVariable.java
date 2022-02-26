package com.nowander.common.interceptor.pathvariable;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author wang tengkun
 * @date 2022/2/25
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface VersifyPathVariable {

    /**
     * 要检验的路径参数变量名
     * @return
     */
    String pathVariableName();

    /**
     * 将url中的参数转换为对应类型
     */
    Class<? extends Function> mapper() default UUIDMapper.class;

    /**
     * 用于检验路径参数是否有效的bean名
     * @return
     */
    String beanName() default "";
    /**
     * 用于检验路径参数是否有效的bean的方法名
     * @return
     */
    String methodName() default "";

    /**
     * 方法参数类型
     * @return
     */
    Class<?>[] methodArgsType() default UUID.class;

    /**
     * 跳过不检验的路径变量
     * @return
     */
    String[] skipParam() default {};

    /**
     * 跳过当前类或方法
     * @return
     */
    boolean skipCurrent() default false;
}
