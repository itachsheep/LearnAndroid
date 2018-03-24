package com.tao.javalib;

/**
 * Created by SDT14324 on 2018/1/4.
 */

public class ProblemTest {
    static class ProductObject {
        public static String value;
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                if (ProductObject.value == null) {
                    ProductObject.value = "NO:" + System.currentTimeMillis();
                    System.out.println("Producer: " + ProductObject.value);
    }}}}

    static class Consumer extends Thread{
        @Override
        public void run() {
            while (true){
                if(ProductObject.value != null){
                    System.out.println("Consumer: "+ProductObject.value);
                    ProductObject.value = null;
    }}}}
    public static void main(String[] args){
        new Producer().start();
        new Consumer().start();
    }
}
