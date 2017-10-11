package com.xutil.contentprovider;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "contProvider";

    public static void log(String msg) {
        if(msg != null) {
            Log.i(TAG, msg);
        }
    }
}
