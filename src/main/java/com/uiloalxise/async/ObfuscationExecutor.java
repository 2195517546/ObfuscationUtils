package com.uiloalxise.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Uiloalxise
 * @ClassName ObfuscationExecutor
 * @Description 图片混淆异步操作的线程池管理器
 */
public class ObfuscationExecutor {

    private static volatile ExecutorService defaultExecutor;
    private static final Object lock = new Object();

    /**
     * 获取默认线程池
     * 使用双重检查锁定模式确保线程安全的单例
     *
     * @return ExecutorService
     */
    public static ExecutorService getExecutor() {
        if (defaultExecutor == null) {
            synchronized (lock) {
                if (defaultExecutor == null) {
                    defaultExecutor = createDefaultExecutor();
                }
            }
        }
        return defaultExecutor;
    }

    /**
     * 创建默认线程池
     * 使用可缓存线程池，适合 IO 密集型任务
     *
     * @return ExecutorService
     */
    private static ExecutorService createDefaultExecutor() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "ObfuscationUtils-Worker-" + threadNumber.getAndIncrement());
                thread.setDaemon(false);
                return thread;
            }
        };

        // 使用固定大小的线程池，大小为 CPU 核心数的 2 倍
        // 适合 IO 密集型操作（图片读写）
        int processors = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(Math.max(processors * 2, 4), threadFactory);
    }

    /**
     * 设置自定义线程池
     *
     * @param executor 自定义线程池
     */
    public static void setExecutor(ExecutorService executor) {
        if (executor == null) {
            throw new IllegalArgumentException("ExecutorService 不能为 null");
        }
        synchronized (lock) {
            // 关闭旧的线程池
            if (defaultExecutor != null && !defaultExecutor.isShutdown()) {
                defaultExecutor.shutdown();
            }
            defaultExecutor = executor;
        }
    }


    /**
     * 关闭线程池
     * 推荐的方法
     */
    public static void shutdown() {
        synchronized (lock) {
            if (defaultExecutor != null && !defaultExecutor.isShutdown()) {
                defaultExecutor.shutdown();
            }
        }
    }



    /**
     * 立即关闭线程池
     */
    public static void shutdownNow() {
        synchronized (lock) {
            if (defaultExecutor != null && !defaultExecutor.isShutdown()) {
                defaultExecutor.shutdownNow();
            }
        }
    }

    /**
     * 关闭线程池（别名）
     */
    public static void close(){
        shutdownNow();
    }

    /**
     * 等待线程池关闭
     *
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 是否在超时前完成关闭
     * @throws InterruptedException 中断异常
     */
    public static boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (lock) {
            if (defaultExecutor != null) {
                return defaultExecutor.awaitTermination(timeout, unit);
            }
            return true;
        }
    }

    /**
     * 检查线程池是否已关闭
     *
     * @return 是否已关闭
     */
    public static boolean isShutdown() {
        synchronized (lock) {
            return defaultExecutor == null || defaultExecutor.isShutdown();
        }
    }

    /**
     * 创建一个新的线程池（不使用默认线程池）
     *
     * @param poolSize 线程池大小
     * @return ExecutorService
     */
    public static ExecutorService createExecutor(int poolSize) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("线程池大小必须大于 0");
        }

        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "ObfuscationUtils-Custom-" + threadNumber.getAndIncrement());
                thread.setDaemon(false);
                return thread;
            }
        };

        return Executors.newFixedThreadPool(poolSize, threadFactory);
    }
}

