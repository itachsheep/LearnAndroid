package com.tao.hidesoftinputdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by SDT14324 on 2017/12/4.
 */

public class HideSoftInputService extends Service {

    private String TAG = "HideSoftInputService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG," onStartCommand ");
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        WindowManager mWm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
//                  View view = new View(getApplicationContext());

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_wm,null);
        view.setBackgroundColor(Color.BLACK);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.width = metrics.widthPixels;
        mParams.height = metrics.heightPixels;
        mParams.gravity = Gravity.TOP | Gravity.CENTER;
//                  mParams.width = 490;//窗口的宽和高
//                  mParams.height = 160;
        mParams.x = 0;
        mParams.y = 0;
        mWm.addView(view, mParams);

        Log.d(TAG," onStartCommand finish..");

        return super.onStartCommand(intent, flags, startId);
    }
}
