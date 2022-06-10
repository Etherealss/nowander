package com.nowander.infrastructure.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class LockKeySpELGenerator implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        return getLockKeyByEl(pjp);
    }

    public String getLockKeyByEl(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        final Object[] args = pjp.getArgs();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        //如果key中含SpEL表达式，先拼接表达式内容
        return SpELParserUtils.parse(method, args, lockAnnotation.key(), String.class);
    }
}
