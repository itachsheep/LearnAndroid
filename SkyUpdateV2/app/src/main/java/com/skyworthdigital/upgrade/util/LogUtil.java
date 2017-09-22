package com.skyworthdigital.upgrade.util;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "skyupgradeV2";

    public static void log(String msg) {
        if(msg != null) {
            Log.i(TAG, msg);
        }
    }
}
