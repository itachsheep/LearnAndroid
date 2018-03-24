package com.tao.viewlearn;

import android.util.Log;

/**
 * Memory
 * Created by tw on 2017/2/7.
 */

public class LogUtil {

    private static boolean isOpen=true;
    public static final String tag = "ViewLearn";
    public static void i(String res){
        Log.i(tag, res);
    }
    public static void e(String res){
        Log.e(tag, res);
    }
}
