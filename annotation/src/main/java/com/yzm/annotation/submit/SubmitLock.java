package com.yzm.annotation.submit;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 重复提交锁
 */
public class SubmitLock {

    private static final ConcurrentHashMap<String, Object> LOCK_CACHE = new ConcurrentHashMap<>(200);
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5);

    /**
     * 静态内部类 单例模式
     */
    private SubmitLock() {
    }

    private static class SingletonInstance {
        private static final SubmitLock INSTANCE = new SubmitLock();
    }

    public static SubmitLock getInstance() {
        return SingletonInstance.INSTANCE;
    }

    /**
     * 加锁 putIfAbsent 是原子操作保证线程安全
     */
    public boolean lock(final String key, Object value) {
        return Objects.nonNull(LOCK_CACHE.putIfAbsent(key, value));
    }

    /**
     * 延时释放锁 用以控制短时间内的重复提交
     */
    public void unLock(final boolean lock, final String key, final int delaySeconds) {
        if (lock) {
            EXECUTOR.schedule(() -> { LOCK_CACHE.remove(key); }, delaySeconds, TimeUnit.SECONDS);
        }
    }
}