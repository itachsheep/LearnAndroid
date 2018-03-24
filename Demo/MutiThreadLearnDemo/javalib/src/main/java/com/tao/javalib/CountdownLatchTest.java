package com.tao.javalib;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用CountDownLatch,在主线程中，开5个子线程来执行10个任务，只有等任务全部完成后，主线程才能再去做其它事。
 * Created by SDT14324 on 2017/12/27.
 */

public class CountdownLatchTest {
    private static int coreSize = 4;
    private static int maxSize = 5;
    private static long keepAlive = 5;
    private static LinkedBlockingQueue<Runnable> threadPoolQueue = new LinkedBlockingQueue<>(20);
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable,"Thread-"+mCount.getAndIncrement());
        }
    };
    private static int workNum = 10;
    private static CountDownLatch countDownLatch = new CountDownLatch(workNum);
   static class MyRunnable implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" finished over ");
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args){
       System.out.println("---------------------main start-------------------");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxSize, keepAlive,
                TimeUnit.SECONDS, threadPoolQueue, threadFactory);
        for(int i = 1; i <= 9; i++){
            executor.execute(new MyRunnable());
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------main end-------------------");
    }

}
