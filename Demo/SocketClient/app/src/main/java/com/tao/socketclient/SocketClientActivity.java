package com.tao.socketclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = SocketClientActivity.class.getSimpleName();
    private final int MSG_RECV = 1;
    private Button btStart;
    private Button btSend;
    private TextView tvRecv;
//    private EditText editText;


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
        btSend = findViewById(R.id.bt_send);
        btStart = findViewById(R.id.bt_start);
//        editText = findViewById(R.id.et);

        tvRecv = findViewById(R.id.tvRecv);
        btStart.setOnClickListener(this);
        btSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_start:
            {
                startClient();
                break;
            }

            case R.id.bt_send:
            {
                /*if(outer != null){
                    Log.i(TAG,"send str 1111 ");
                    outer.write("1111");
                    outer.flush();
                }*/
                break;
            }

        }
    }

    private void startClient() {
        new Thread(){
            @Override
            public void run() {
                PrintWriter write = null;
                BufferedReader in = null;
                Socket socket = null;
                Log.i(TAG,"startClient  ");
                try {
                    //socket = new Socket("127.0.0.1", 5209);
                    socket = new Socket("192.168.1.113", 5209);
                    Log.i(TAG,"客户端启动成功");
                    write = new PrintWriter(socket.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    int i = 0;
                    while (true){
                        write.println("hello i am client "+(i++));
                        write.flush();
                        sendMessage(in.readLine());
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    Log.i(TAG,"startClient  IOException "+e.getMessage());
                    e.printStackTrace();
                }finally {
                    try {
                        in.close();
                        write.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    private void sendMessage(String receiveMes) {
        Log.i(TAG,"sendMessage  receiveMes "+receiveMes);
        Message msg = mHandler.obtainMessage(MSG_RECV);
        msg.obj = receiveMes;
        mHandler.sendMessage(msg);
    }
}
