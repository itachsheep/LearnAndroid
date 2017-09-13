package com.skyworth.base;

import android.os.StrictMode;

import com.skyworth.exception.OomExceptionHandler;
import com.skyworth.utils.L;
import com.skyworth.utils.SharePreferenceManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by SDT14324 on 2017/9/13.
 */

public class DebugApplication extends SkyworthApplication {
    private String tag = "DebugApplication";

    @Override
    protected RefWatcher installLeakCanary() {
        L.i(tag,"installLeakCanary ..");
        enabledStrictMode();
        RefWatcher refWatcher = LeakCanary.install(this);

        OomExceptionHandler.install(this);

        GlobalContext.init(getApplicationContext());
        SharePreferenceManager.init();
        return refWatcher;
    }


    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }


}
