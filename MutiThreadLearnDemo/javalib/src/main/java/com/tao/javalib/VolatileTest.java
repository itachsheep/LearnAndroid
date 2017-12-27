package com.tao.javalib;

/**
 * volatile关键字使用
 * Created by SDT14324 on 2017/12/21.
 */

public class VolatileTest {
    static long SerialNum = 0;
    static class ProductObject {
        //使用互斥锁
        // public static String value;
        //保证线程可见性，但不保证操作原子性
        public static volatile String value;
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            while (true){
                if(ProductObject.value == null){
                    ProductObject.value = "NO:"+System.currentTimeMillis()+"_"+SerialNum;
                    System.out.println("Producer: "+ProductObject.value);
                    SerialNum++;
                }
            }
        }
    }

    static class Consumer extends Thread{
        @Override
        public void run() {
            while (true){
                //使用volatile保证线程可见性，但不保证操作原子性，如果a = a+1，从1到1000往上加，往往最后得到的小于1000
                if(ProductObject.value != null){
                    System.out.println("Consumer: "+ProductObject.value);
                    ProductObject.value = null;
                }
            }
        }
    }

    public static void main(String[] args){
        Object lock = new Object();
        new ThreadAsyncTest.Producer(lock).start();
        new ThreadAsyncTest.Consumer(lock).start();
    }
}
