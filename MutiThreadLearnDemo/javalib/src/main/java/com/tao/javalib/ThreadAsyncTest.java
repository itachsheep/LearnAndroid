package com.tao.javalib;

/**
 * 多线程同步 synchronized waite notify
 * Created by SDT14324 on 2017/12/20.
 */

public class ThreadAsyncTest {
    static long SerialNum = 0;
    static class ProductObject {
        //使用互斥锁
        public static String value;
        //保证线程可见性，但不保证操作原子性
//        public static volatile String value;
    }

    static class Producer extends Thread {
        private Object lock;
        public Producer(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(ProductObject.value != null){
                        try {
                            System.out.println("Producer wait");
                            lock.wait();
                            System.out.println("Producer wait after");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ProductObject.value = "NO:"+System.currentTimeMillis()+"_"+SerialNum;
                    System.out.println("Producer: "+ProductObject.value);
                    SerialNum++;
                    lock.notify();
                }
            }
        }
    }

    static class Consumer extends Thread{
        private Object lock;
        public Consumer(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(ProductObject.value == null){
                        try {
                            System.out.println("Consumer wait");
                            lock.wait();
                            System.out.println("Consumer wait after");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Consumer: "+ProductObject.value);
                    ProductObject.value = null;
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args){
        Object lock = new Object();
        new Producer(lock).start();
        new Consumer(lock).start();
    }
}
