package com.skyworth.base;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by SDT14324 on 2017/9/13.
 */

public class SkyworthApplication extends Application {

    protected RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context){
        SkyworthApplication application = (SkyworthApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
    }
    protected void setupLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = installLeakCanary();
    }

    protected RefWatcher installLeakCanary(){
        return RefWatcher.DISABLED;
    }
}
