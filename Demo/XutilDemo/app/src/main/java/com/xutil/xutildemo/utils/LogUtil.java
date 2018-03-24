package com.xutil.xutildemo.utils;

import android.util.Log;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class LogUtil {
    public static final String TAG = "xutilsDemo";

    public static void i(String msg) {
        Log.i(getTag(), msg);
    }

    private static String getTag(){
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
//        tag = TextUtils.isEmpty(TAG) ? tag : TAG + ":" + tag;
        tag = TAG+"."+tag;
        return tag;
    }
}
