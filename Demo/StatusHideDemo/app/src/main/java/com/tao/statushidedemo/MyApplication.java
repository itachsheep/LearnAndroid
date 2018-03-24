package com.tao.statushidedemo;

import android.app.Application;

/**
 * Created by SDT14324 on 2018/3/19.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;
    public static MyApplication getApp() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
