package utils;

import android.os.SystemClock;
import android.util.Log;

import com.zyxnet.autonavi.BuildConfig;

/**
 * Created by SDT14324 on 2017/8/31.
 */

public class LogUtils {
    private static String tag = "SkyNavi";
    private static boolean isDebug = BuildConfig.LOG_DEBUG;
    public static void i(String mes){
        if(isDebug){
            Log.i(tag,mes+" , "+SystemClock.uptimeMillis());
        }
    }
}
