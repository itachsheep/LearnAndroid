package com.tao.handlerlearndemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by SDT14324 on 2018/2/9.
 */

public class HandlerDispatchActivity extends Activity implements View.OnClickListener {
    private String TAG = HandlerDispatchActivity.class.getSimpleName();
    private Button btSend;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                {
                    try {
                        Log.i(TAG,"### deal msg 1, start sleep 3000ms, msg = "+msg);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG,"### deal msg 1, end sleep ");
                }
                    break;
                case 2:
                {
                    Log.i(TAG,"deal msg 2, msg = "+msg);
                }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_dispatch);
        btSend = findViewById(R.id.bt_send);
        btSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_send:
            {
                mHandler.sendEmptyMessage(1);
                for (int i = 0 ; i < 50 ; i++){
                    mHandler.sendEmptyMessage(2);
                }
            }
                break;
        }
    }
}
