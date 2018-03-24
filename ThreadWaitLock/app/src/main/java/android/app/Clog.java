package android.app;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by taow on 2017/8/2.
 */

public class Clog {
    private static String tag = "ThreadWaitLock";
    public static void i(String mes){
        Log.i(tag,mes +", time: "+ SystemClock.uptimeMillis());
    }
}
