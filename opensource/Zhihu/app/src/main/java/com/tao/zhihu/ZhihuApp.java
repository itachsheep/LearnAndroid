package com.tao.zhihu;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class ZhihuApp extends Application {

    public static String sPackageName;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initPackageName();
    }

    private void initPackageName() {
        PackageInfo info;
        try {
            info = getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(), 0);
            sPackageName = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
