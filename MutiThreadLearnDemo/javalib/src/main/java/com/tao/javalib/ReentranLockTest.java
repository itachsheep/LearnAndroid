package com.tao.javalib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程顺序打印A B C
 * Created by SDT14324 on 2017/12/27.
 */

public class ReentranLockTest {
    private static Lock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();
    private static String type = "A";

    static class  RunnableA implements Runnable {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (!type.equals("A")) {
                        try {
                            conditionA.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + type);
                    type = "B";
                    conditionB.signal();
                } finally {
                    //System.out.println(Thread.currentThread().getName() + " unlock ");
                    lock.unlock();
                }
            }
        }
    }


    static class RunnableB implements Runnable {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        if (!type.equals("B")) {
                            try {
                                conditionB.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + ": " + type);
                        type = "C";
                        conditionC.signal();
                    } finally {
                        //System.out.println(Thread.currentThread().getName() + " unlock ");
                        lock.unlock();
                    }
                }
            }
        }

    static class RunnableC implements Runnable {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (!type.equals("C")) {
                        try {
                            conditionC.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + type);
                    type = "A";
                    conditionA.signal();
                } finally {
                    //System.out.println(Thread.currentThread().getName() + " unlock ");
                    lock.unlock();
                }
            }
        }
    }
        public static void main(String[] args) {
            new Thread(new RunnableA()).start();
            new Thread(new RunnableB()).start();
            new Thread(new RunnableC()).start();
        }

}
