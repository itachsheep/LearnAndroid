package com.tao.javalib;

public class myClass {

    public static void main(String[] args){
        System.out.println("loop begin..");
        loop();
        System.out.println("loop end..");
    }

    public static void loop(){
        for(;;){

            int a = next();
            System.out.println("a = "+a);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*if(a != 2){
                return;
            }*/

        }
    }

    public static int next(){
        for(;;){

        }
    }
}
