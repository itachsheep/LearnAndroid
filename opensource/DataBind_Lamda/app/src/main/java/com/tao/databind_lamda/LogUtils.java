package com.tao.databind_lamda;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/10/18.
 */

public class LogUtils {
    public static final String tag = "DataBindingDemo";

    public static void i(String mes){
        Log.i(tag,getClassName()+mes);
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
