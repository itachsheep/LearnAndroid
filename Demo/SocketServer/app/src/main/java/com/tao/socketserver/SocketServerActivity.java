package com.tao.socketserver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SocketServerActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = SocketServerActivity.class.getSimpleName();
    private final int MSG_RECV = 1;
    private Button btStart;
    private TextView tvRecv;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_RECV:
                {
                    tvRecv.setText((String)msg.obj);
                }
                    break;
                case 2:

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStart = findViewById(R.id.bt_start);
        tvRecv = findViewById(R.id.tvRecv);
        btStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_start){
            startServer();
        }
    }

    private void startServer() {
        startService(new Intent(SocketServerActivity.this,MyService.class));
    }

    private void sendMessage(String receiveMes) {
        Log.i(TAG,"sendMessage  receiveMes "+receiveMes);
        Message msg = mHandler.obtainMessage(MSG_RECV);
        msg.obj = receiveMes;
        mHandler.sendMessage(msg);
    }
}
