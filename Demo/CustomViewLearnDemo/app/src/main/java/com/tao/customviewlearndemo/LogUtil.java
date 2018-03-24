package com.tao.customviewlearndemo;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/12/15.
 */

public class LogUtil {
    private static String TAG = "CustomViewLearn";
    public static void i(String tag,String mes){
        Log.d(TAG,tag+"."+mes);
    }
}
