package com.tao.ndksearch;

import android.util.Log;

public class LogUtils {
    private static String TAG = "NdkDemo";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
}
