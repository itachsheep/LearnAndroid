package com.tao.javalib;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 十个运动员在同一起跑线都准备好，裁判给哨子，开始跑
 * Created by SDT14324 on 2017/12/27.
 */

public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    static class MyRunnable implements Runnable{
        private final static AtomicInteger mCount = new AtomicInteger(0);
        private String name ;
        public MyRunnable(){
            name = Thread.currentThread().getName()+"-"+mCount.getAndIncrement();
        }
        @Override
        public void run() {
            try {
                System.out.println(name+" wait others");
                cyclicBarrier.await();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }finally {
                System.out.println(name+" is finished");
            }
        }
    }

    public static void main(String[] args){
        Executor executor = Executors.newFixedThreadPool(10);
        for(int i = 0 ; i < 10; i++){
            executor.execute(new MyRunnable());
        }
    }
}
