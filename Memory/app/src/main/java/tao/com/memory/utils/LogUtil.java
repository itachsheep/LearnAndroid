package tao.com.memory.utils;

import android.util.Log;

/**
 * Memory
 * Created by tw on 2017/2/7.
 */

public class LogUtil {

    private static boolean isOpen=true;
    public static final String tag = "memory";
    public static void i(String res){
        Log.i(tag, res);
    }
    public static void e(String res){
        Log.e(tag, res);
    }
}
