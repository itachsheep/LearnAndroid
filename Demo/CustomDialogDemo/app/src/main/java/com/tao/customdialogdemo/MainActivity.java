package com.tao.customdialogdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.i(TAG,"dpi = "+displayMetrics.densityDpi+" desity = "+displayMetrics.density);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams(
                200,
                100,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSPARENT
        );
        Button bt =  (Button) findViewById(R.id.main_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(MainActivity.this,R.layout.view_add_wm,null);
                // flag 设置 Window 属性
               // layoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                // type 设置 Window 类别（层级）
                //layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                //layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
               //layoutParams.type = WindowManager.LayoutParams.TYPE_NAVIGATION_BAR;
                layoutParams.gravity = Gravity.CENTER;
                windowManager.addView(view, layoutParams);
            }
        });
    }
}
