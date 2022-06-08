package com.nowander.infrastructure.lock;

import java.lang.annotation.*;

/**
 * @author wang tengkun
 * @date 2022/6/8
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
