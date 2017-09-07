package utils;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by SDT14324 on 2017/9/4.
 */

public class LogUtils {
    private static String tag = "NaviTao";
    public static void i(String mes){
        Log.i(tag,mes + ", time:"+ SystemClock.uptimeMillis()/1000);
    }
}
