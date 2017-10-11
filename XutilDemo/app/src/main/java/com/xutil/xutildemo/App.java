package com.xutil.xutildemo;

import android.app.Application;

import com.xutil.xutildemo.db.StuManager;

import org.xutils.x;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class App extends Application {
    private static App instance;

    private StuManager stuManager;
    public static App getInstance(){
        return instance;
    }

    public StuManager getStuManager(){
        return stuManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        stuManager = new StuManager();
        x.Ext.init(this);
    }


}
