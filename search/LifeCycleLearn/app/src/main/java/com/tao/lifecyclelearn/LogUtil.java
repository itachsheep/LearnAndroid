package com.tao.lifecyclelearn;

import android.util.Log;

public class LogUtil {
    private static String TAG = "LifeCycle";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
}
