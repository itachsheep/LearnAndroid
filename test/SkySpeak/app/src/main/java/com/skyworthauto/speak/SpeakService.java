package com.skyworthauto.speak;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.skyworthauto.speak.txz.SkySpeakManager;
import com.skyworthauto.speak.util.L;

public class SpeakService extends Service {
    private static final String TAG = "SpeakService";

    private SkySpeakManager mTxzMamager;

    public static void startService(Context context) {
        Intent in = new Intent(context, SpeakService.class);
        context.startService(in);
    }

    public static void stopService(Context context) {
        Intent in = new Intent(context, SpeakService.class);
        context.stopService(in);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.d(TAG, "SpeakService.onCreate");

        mTxzMamager = new SkySpeakManager(this);
        mTxzMamager.init();

        //        McuServiceManager.init();
        //        OnlineMusicManager.init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(TAG, "SpeakService.onDestroy");
        if (mTxzMamager != null) {
            mTxzMamager.exit();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
