package com.tao.weexsearch.module;

import android.util.Log;
import android.widget.Toast;

import com.tao.weexsearch.WXApplication;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by liuzhao on 2017/10/23.
 *
 * 展示自定义Module使用
 *
 */
public class CommonModule extends WXModule {
    private String TAG = CommonModule.class.getSimpleName();
    @JSMethod(uiThread = true)
    public void toast(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Log.i(TAG,"----------------------------------------------------");
        for(int i  = 0; i < stackTrace.length; i++){
            Log.i(TAG,stackTrace[i].getClassName()+"."+
                    stackTrace[i].getMethodName()+": "+stackTrace[i].getLineNumber()+" ");
        }
        Toast.makeText(WXApplication.getApp(), "哈哈哈哈哈xxx！！" + message, Toast.LENGTH_LONG).show();
    }

}
