package com.skyworthauto.speak.util;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/11/21.
 */

public class LogUtils {
    private static final String tag = "test11";
    public static void i(String mes){
        Log.i(tag,mes);
    }

    public static void i(String tag1,String mes){
        Log.i(tag,tag1+","+mes);
    }

}
