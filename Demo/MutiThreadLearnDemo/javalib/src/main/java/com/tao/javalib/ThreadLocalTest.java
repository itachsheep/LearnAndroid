package com.tao.javalib;

/**
 * Created by SDT14324 on 2018/1/4.
 */

public class ThreadLocalTest {
    static ThreadLocal<Integer> var = new ThreadLocal<>();

    static class ThreadA extends Thread{
        @Override
        public void run() {
            var.set(0);
            while (true){
                Integer i = var.get();
                i = i+100;
                var.set(i);
                System.out.println(Thread.currentThread().getName()+" - "+var.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    static class ThreadB extends Thread{
        @Override
        public void run() {
            var.set(0);
            while (true){
                Integer i = var.get();
                i = i+1;
                var.set(i);
                System.out.println(Thread.currentThread().getName()+" - "+var.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public static void main(String[] args){
        new ThreadA().start();
        new ThreadB().start();
    }
}
