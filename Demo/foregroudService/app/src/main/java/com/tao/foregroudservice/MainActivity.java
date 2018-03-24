package com.tao.foregroudservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by SDT14324 on 2017/12/4.
 */

public class MainActivity extends Activity {
    private String TAG = "MainActivity";
    private WindowManager wm ;
//    private EditText mEt;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    showWindow();
                    break;

                case 2:
                    Intent intent = new Intent("com.tao.hide.softinput.service");
                    startService(intent);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


//        mEt = (EditText) findViewById(R.id.et_main);
        findViewById(R.id.bt_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mHandler.sendEmptyMessageDelayed(2,1500);
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
            }
        });
    }

    private void showWindow(){
        WindowManager mWm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
//      View view = new View(getApplicationContext());
        View view = getLayoutInflater().inflate(R.layout.layout_wm,null);
        view.setBackgroundColor(Color.BLACK);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.width = metrics.widthPixels;
        mParams.height = metrics.heightPixels;
        mParams.gravity = Gravity.TOP | Gravity.CENTER;
//      mParams.width = 490;//窗口的宽和高
//      mParams.height = 160;
        mParams.x = 0;
        mParams.y = 0;
        mWm.addView(view, mParams);
    }


}
