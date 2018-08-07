package com.github.piasy.openglestutorial_android;

import android.util.Log;

public class LogUtils {
    private static String TAG = "GlesDemo";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
}
