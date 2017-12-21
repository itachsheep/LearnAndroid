package com.tao.mutithreadlearndemo;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/12/21.
 */

public class LogUtil {
    private static String TAG = "MutiThread";
    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }
}
