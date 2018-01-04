package com.tao.javalib;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 模拟实现AsyncTask
 * Created by SDT14324 on 2017/12/25.
 */

public class AsyncTaskImpl {

    static int total;
    static class MyRunnable implements Runnable{
        private String name;

        public MyRunnable(){
            total++;
            name = Thread.currentThread().getName() +" num = "+total;
        }

        public String getName(){
            return name;
        }
        @Override
        public void run() {
//            name = Thread.currentThread().getName() +" num = "+name;
            while (true){
                System.out.println("MyRunnable "+name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args){
        int corePoolSize = 4;
        int maxPoolSize = 5;
        int keepAlive = 10;
        BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(10);
        ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                String name = "AsyncTaskImpl #"+mCount.getAndIncrement();
                System.out.println("ThreadFactory "+mCount.get());
                return new Thread(r,name);
            }
        };

        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.out.println("######### "+((MyRunnable)runnable).getName() + "is reject ############");
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAlive, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory,handler);
        for(int i = 1 ; i <= 16; i++ ){
            threadPoolExecutor.execute(new MyRunnable());
        }
    }
}
