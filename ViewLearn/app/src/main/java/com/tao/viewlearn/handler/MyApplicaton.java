package com.tao.viewlearn.handler;

import android.app.Application;

import com.tao.viewlearn.LogUtil;

/**
 * Created by taow on 2017/8/8.
 */

public class MyApplicaton extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("onCreate.");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.i("onLowMemory.");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.i("onTrimMemory. level: "+level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.i("onTerminate.");
    }
}
