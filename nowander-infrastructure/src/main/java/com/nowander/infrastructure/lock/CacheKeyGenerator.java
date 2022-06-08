package com.nowander.infrastructure.lock;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * CacheKeyGenerator
 * @author wang tengkun
 * @date 2022/6/7
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
