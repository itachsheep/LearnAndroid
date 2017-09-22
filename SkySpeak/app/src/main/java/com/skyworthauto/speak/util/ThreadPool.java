package com.skyworthauto.speak.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private static final String TAG = "ThreadPool";

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 4;
    private static final long KEEP_ALIVE_TIME = 60L; // second

    private static ExecutorService sThreadPool;

    private static ExecutorService getThreadPool() {
        if (sThreadPool == null) {
            sThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        }
        return sThreadPool;
    }

    public static void execute(Runnable task) {
        try {
            getThreadPool().execute(task);
        } catch (RejectedExecutionException e) {
            L.d(TAG, "ThreadPool name = " + task.getClass() + e);
        }
    }
}
