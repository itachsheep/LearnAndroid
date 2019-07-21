package com.tao.wei.hybirdflutter;

import android.util.Log;

public class LogUtil {
    private static String TAG = "flutter_boost.";
    public static void d(String tag,String mes){
        Log.d(TAG + tag,mes);
    }

    public static void printStack(String tag){
        RuntimeException e = new RuntimeException();
        e.fillInStackTrace();
        Log.d(TAG + tag,"--------------------------------",e);
    }
}
