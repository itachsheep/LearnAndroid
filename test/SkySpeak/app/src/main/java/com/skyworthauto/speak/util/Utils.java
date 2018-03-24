package com.skyworthauto.speak.util;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.skyworthauto.speak.SpeakApp;

import java.util.List;

public class Utils {
    private static final String TAG = "PhoneUtils";

    public static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) SpeakApp.getApp()
                    .getSystemService(Service.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            L.d(TAG, "getIMEI exception:" + e.getMessage());
            return "";
        }
    }

    public static String getString(int resId) {
        return SpeakApp.getApp().getString(resId);
    }

    public static int getInt(int resId) {
        return SpeakApp.getApp().getResources().getInteger(resId);
    }

    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        L.d(TAG, "onReceive netInfo=" + netInfo);
        return netInfo != null && netInfo.isConnected();
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (packageName.equals(packageInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPackageInstalled(Context context, String... packages) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageList = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageList) {
            for (int i = 0; i < packages.length; i++) {
                if (packages[i].equals(packageInfo.packageName)) {
                    return true;
                }
            }

        }
        return false;
    }
}
