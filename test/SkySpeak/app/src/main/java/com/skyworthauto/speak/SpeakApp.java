package com.skyworthauto.speak;

import android.os.Handler;
import android.os.Looper;

import com.skyworthauto.sdk.SkyBaseApplication;
import com.skyworthauto.speak.txz.AudioControl;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.SkyPreferenceManager;

public class SpeakApp extends SkyBaseApplication {
    private final static String TAG = "SpeakApp";

    private static SpeakApp sInstance;

    private static Handler sUiHandler = new Handler(Looper.getMainLooper());

    public static SpeakApp getApp() {
        return sInstance;
    }

    public static void runOnUiGround(Runnable r, long delay) {
        if (delay > 0) {
            sUiHandler.postDelayed(r, delay);
        } else {
            sUiHandler.post(r);
        }
    }

    public static void removeUiGroundCallback(Runnable r) {
        sUiHandler.removeCallbacks(r);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        L.d(TAG, "app onCreate");
        Config.init();
        SkyPreferenceManager.init(this);
        SpeakService.startService(this);
    }

    @Override
    public void onAppKillCallBack() {
        L.d(TAG, "onAppKillCallBack");
        AudioControl.getInstance().reset();
    }

}
