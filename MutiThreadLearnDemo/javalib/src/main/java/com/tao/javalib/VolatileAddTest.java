package com.tao.javalib;

/**
 * Created by SDT14324 on 2018/1/4.
 */

public class VolatileAddTest {
    static volatile int a = 0;
    static class AddThread extends Thread{
        @Override
        public void run() {
            a = a +1;
        }
    }
    public static void main(String[] args){
        for(int i = 1 ; i <= 1000; i++){
            new AddThread().start();
        }
        System.out.println(" a = "+a);
    }
}
