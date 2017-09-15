package com.tao.usecase.utils;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class LogUtils {
    private static String tag = "UseCase";
    public static void i(String mes){
        Log.i(tag,mes+" , "+ SystemClock.uptimeMillis()/1000);
    }

    public static void e(String mes){
        Log.e(tag,mes+" , "+ SystemClock.uptimeMillis()/1000);
    }
}
