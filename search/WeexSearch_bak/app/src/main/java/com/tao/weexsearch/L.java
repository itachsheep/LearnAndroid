package com.tao.weexsearch;

import android.util.Log;

public class L {
    private static String TAG = "Weex";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
    public static void printStack(String tag,String method){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Log.i(TAG,"printStack ---------------------------------------------------- "+method);
        for(int i  = 0; i < stackTrace.length; i++){
            Log.i("",stackTrace[i].getClassName()+"."+
                    stackTrace[i].getMethodName()+": "+stackTrace[i].getLineNumber()+" ");
        }
    }
}
