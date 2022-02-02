package com.nowander.like.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wtk
 * @date 2022-01-29
 */
public class NamedThreadFactory implements ThreadFactory {
    /**
     * 线程池号（原子操作）
     */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    /**
     * 线程组
     */
    private final ThreadGroup GROUP;
    /**
     * 线程号（原子操作）
     */
    private final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    /**
     * 线程名前缀
     */
    private final String NAME_PREFIX;

    /**
     * 提供线程命名
     * @param name 线程名
     */
    public NamedThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        GROUP = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

        //不重复命名而影响原有名称
        if (null == name || name.isEmpty()) {
            name = "pool";
        }
        //拼接前缀名
        NAME_PREFIX = name + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
		/*
		ThreadGroup group,
        Runnable target,
        String name,
        long stackSize
        getAndIncrement() 不断尝试自增
		 */
        Thread t = new Thread(GROUP, r, NAME_PREFIX + THREAD_NUMBER.getAndIncrement(), 0);
        //非守护线程
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
