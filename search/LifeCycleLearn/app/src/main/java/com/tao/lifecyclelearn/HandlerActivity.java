package com.tao.lifecyclelearn;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by taowei on 2018/6/24.
 * 2018-06-24 15:15
 * LifeCycleLearn
 * com.tao.lifecyclelearn
 */

public class HandlerActivity extends AppCompatActivity {
    private String TAG = HandlerActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);


    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            
        }
    };
    public void startb(View view){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG,"延时10s : "+ System.currentTimeMillis()/1000);
            }
        },10 * 1000);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG,"延时5s : "+System.currentTimeMillis()/1000);
            }
        },5 * 1000);

        try {
            LogUtil.i(TAG,"start sleep : "+ System.currentTimeMillis()/1000);
            Thread.sleep(15 * 1000);
            LogUtil.i(TAG,"sleep finished : "+ System.currentTimeMillis()/1000);
        } catch (InterruptedException e) {
            LogUtil.i(TAG,"sleep exception");
            e.printStackTrace();
        }
    }
}
