package com.nowander.like.pool;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;


/**
 * @author wtk
 * @date 2022-01-29
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "nowander.like.save")
@Setter
public class SaveLikeThreadPool {

    private int workQueueCapacity = 5;
    private long keepAliveTime = 5;
    private TimeUnit unit = TimeUnit.MINUTES;
    private int corePoolSize = 5;
    private int maxPoolSize = 10;

    private NamedThreadFactory namedThreadFactory = new NamedThreadFactory("saveLikeThreadFactory");

    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(workQueueCapacity);

    private ThreadPoolExecutor pool = new ThreadPoolExecutor(
            corePoolSize, maxPoolSize, keepAliveTime,
            unit, workQueue, namedThreadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy());

    public Future<?> submit(Runnable runnable) {
        return pool.submit(runnable);
    }

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }
}
