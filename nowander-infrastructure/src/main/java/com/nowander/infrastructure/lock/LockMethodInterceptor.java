package com.nowander.infrastructure.lock;

import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;


/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@Profile("!standalone")
@Aspect
@Slf4j(topic = "cache-lock")
@Configuration
public class LockMethodInterceptor {

    private final RedisLockHelper redisLockHelper;
    private final CacheKeyGenerator cacheKeyGenerator;

    private final String lockPrefix;

    @Autowired
    public LockMethodInterceptor(RedisLockHelper redisLockHelper,
                                 CacheKeyGenerator cacheKeyGenerator,
                                 @Value("${app.lock.redis.key-prefix}") String lockPrefix) {
        this.redisLockHelper = redisLockHelper;
        this.cacheKeyGenerator = cacheKeyGenerator;
        this.lockPrefix = lockPrefix;
    }

    @Around("execution(public * *(..)) && @annotation(com.nowander.infrastructure.lock.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.hasText(lock.key())) {
            log.error("@CacheLock 指定的key不能为空");
            throw new RuntimeException("cache lock key should not be empty");
        }

        final String lockKey = lockPrefix + cacheKeyGenerator.getLockKey(pjp);
        final String lockFlag = UUID.randomUUID().toString();
        long startTime = -1;
        try {
            // 循环重试
            for (int retryTimes = lock.retryTimes(); retryTimes >= 0; retryTimes--) {
                boolean lockSuccess = redisLockHelper.lock(lockKey, lockFlag, lock.expire(), lock.timeUnit());
                if (lockSuccess) {
                    // 获取锁成功
                    log.debug("获取分布式锁成功，key: {}, value: {}，重试次数: {}", lockKey, lockFlag, lock.retryTimes() - retryTimes);
                    startTime = System.currentTimeMillis();
                    return pjp.proceed();
                }
                // 获取锁失败
                long sleepMs = lock.retryIntervalMs();
                log.debug("获取分布式锁失败。key: {}, value: {}，剩余重试次数：{}，睡眠时间：{}ms", lockKey, lockFlag, retryTimes, sleepMs);
                if (sleepMs > 0) {
                    try {
                        Thread.sleep(sleepMs);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            // 重试次数用尽也没有获取锁
            if (lock.lockFailedThrowException()) {
                throw new BaseException(ApiInfo.SERVER_BUSY, "业务繁忙，请稍后重试");
            } else {
                return null;
            }
        } finally {
            // 释放锁
            redisLockHelper.unlockLua(lockKey, lockFlag);
            log.debug("释放分布式锁成功，key: {}, value: {}, 占有锁的时长: {}ms", lockKey, lockFlag, startTime > 0 ? System.currentTimeMillis() - startTime : 0);
        }
    }
}
