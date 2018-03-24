package com.java.eventbusdemo.utils;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class LogUtils {
    private static String tag = "EventBusDemo";
    public static void i(String mes){
        Log.i(tag,mes);
    }

    public static void e(String mes){
        Log.e(tag,mes);
    }
}
