package com.skyworthauto.speak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.Utils;

public class SkyReceiver extends BroadcastReceiver {
    private static final String TAG = "Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        L.d(TAG, "onReceive action=" + action);
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            startServiceWhenConnected(context);
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            SpeakService.startService(context);
        }

    }

    private void startServiceWhenConnected(Context context) {
        if (Utils.isNetConnected(context)) {
            SpeakService.startService(context);
        }
    }
}
