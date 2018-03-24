package com.tao.contextdemo;

import android.util.Log;

/**
 * Created by SDT14324 on 2018/2/2.
 */

public class LogUtil {
    private static String TAG = "CtxLrn";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
}
