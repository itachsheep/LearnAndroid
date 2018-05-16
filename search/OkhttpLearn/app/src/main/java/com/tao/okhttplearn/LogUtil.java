package com.tao.okhttplearn;

import android.util.Log;

/**
 * Created by SDT14324 on 2018/3/16.
 */

public class LogUtil {
    private static String TAG = "Just";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
}
