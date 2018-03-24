package com.tao.javalib;

import java.util.concurrent.CountDownLatch;

/**
 * Created by SDT14324 on 2018/1/4.
 */

public class CountDownTest2 {
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
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

        for(int i = 1; i <= 10; i++){
            new Thread(new MyRunnable()).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------main end-------------------");
    }
}
