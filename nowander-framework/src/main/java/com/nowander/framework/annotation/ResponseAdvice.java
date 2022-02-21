package com.nowander.framework.annotation;

import java.lang.annotation.*;

/**
 * 标记是否使用 GlobalResponseHandler 进行返回值处理
 * @author wtk
 * @date 2022/2/21
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 类或方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseAdvice {
}
