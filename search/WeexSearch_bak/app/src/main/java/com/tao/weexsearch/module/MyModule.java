package com.tao.weexsearch.module;

import android.widget.Toast;

import com.tao.weexsearch.L;
import com.tao.weexsearch.WXApplication;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by taowei on 2018/6/6.
 * 2018-06-06 22:40
 * WeexSearch_bak
 * com.tao.weexsearch.module
 */

public class MyModule extends WXModule {
    private String TAG = MyModule.class.getSimpleName();
    //run ui thread
//    @JSMethod(uiThread = true)
//    public void printLog(String msg) {
//        L.i(TAG,"## printLog ##" );
//        Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_LONG).show();
//    }

    @JSMethod(uiThread = true)
    public void printLog(String message) {
        L.i(TAG,"android printLog");
//        L.printStack(TAG,"printLog");
        Toast.makeText(WXApplication.getApp(), "哈哈哈哈哈xxx！！" + message, Toast.LENGTH_LONG).show();
    }
}
