package com.tao.ndksearch;

public class JavaObject {
    public int mNum = 10;
    public static int sNum = 10;

    public JavaObject(){

    }

    public JavaObject(int num){
        this.mNum = num;
    }

    public int method(){
        return 20;
    }

    public static int methodStatic(){
        return 2000;
    }

    @Override
    public String toString() {
        return "[JavaObject] mNum = " + mNum + " sNum = " + sNum;
    }
}
