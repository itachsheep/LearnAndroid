package com.skyworthauto.speak;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


public class SDKDemoApp extends Application {
	public final static String TAG = "SDKDemoApp";

	private static SDKDemoApp instance;
	protected static Handler uiHandler = new Handler(Looper.getMainLooper());

	public static SDKDemoApp getApp() {
		return instance;
	}

	public static void runOnUiGround(Runnable r, long delay) {
		if (delay > 0) {
			uiHandler.postDelayed(r, delay);
		} else {
			uiHandler.post(r);
		}
	}

	public static void removeUiGroundCallback(Runnable r) {
		uiHandler.removeCallbacks(r);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("TestDemo", "oncreate.. ");
		instance = this;
	}
}
