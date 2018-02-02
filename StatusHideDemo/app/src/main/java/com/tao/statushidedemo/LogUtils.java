package com.tao.statushidedemo;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/10/18.
 */

public class LogUtils {
    public static final String TAG = "FullScreenLrn";

    public static void i(String tag,String mes){
        Log.i(TAG,tag+"."+mes);
    }


    public static String getClassName(){
        String res = "%s.%s(L:%d)";
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        String className = stackTraceElement.getClassName();
        className = className.substring(className.lastIndexOf(".")+1);
        res = String.format(res,className,stackTraceElement.getMethodName(),stackTraceElement.getLineNumber());
        return res;
    }




}
