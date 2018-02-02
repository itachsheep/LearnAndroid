package com.tao.contextdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by SDT14324 on 2018/2/2.
 */

public class MyApplication extends Application {
    private String TAG = MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();

        Context applicationContext = getApplicationContext();
        Context baseContext = getBaseContext();
        LogUtil.i(TAG,"onCreate applicationContext = "+applicationContext
        +", baseContext = "+baseContext);

    }
}
