package com.tao.javalib;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by SDT14324 on 2017/12/22.
 */

public class FutureTest1 {
    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            int i = 0;
            for(; i < 10; i++){
                try {
                    System.out.println(Thread.currentThread().getName()+"_"+i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }

        public static void main(String[] args){
            Task callable = new Task();
            FutureTask<Integer> future = new FutureTask<Integer>(callable){
                @Override
                protected void done() {
                    try {
                        Integer integer = get();//阻塞等待，获取异步任务返回值
                        System.out.println("done : "+integer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            //创建默认线程池
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.execute(future);

            System.out.println("##### finished ####");
        }
    }
}
