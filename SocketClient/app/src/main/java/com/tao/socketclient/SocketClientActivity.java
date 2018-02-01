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
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
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
                if(outer != null){
                    Log.i(TAG,"send str 1111 ");
                    outer.write("1111");
                    outer.flush();
                }
                break;
            }

        }
    }

    private PrintWriter outer = null;
    private void startClient() {
        new Thread(){
            @Override
            public void run() {
                Log.i(TAG,"startClient  ");
                BufferedReader in = null;
                Socket mSocket = null;
                try {
//                    mSocket = new Socket("127.0.0.1", 8888);
                    mSocket = new Socket("192.168.1.111", 8000);
                    mSocket.connect(new InetSocketAddress("192.168.1.111",8000));

                    byte[] buffer = new byte[20 * 1024];
                    while (true){
                        InputStream inputStream = mSocket.getInputStream();
                        outer = new PrintWriter(mSocket.getOutputStream());
                        String s = "";
                        while (inputStream.read(buffer) != -1){
                            s = new String(buffer);
                        }
//                        String receiveMes = in.readLine();
                        Log.i(TAG,"startClient  receiveMes "+s);
//                        sendMessage(s);
                    }
                } catch (IOException e) {
                    Log.i(TAG,"startClient  IOException "+e.getMessage());
                    e.printStackTrace();
                }finally {
//                    try {
//                        in.close();
//                        outer.close();
//                        mSocket.close();
//                    } catch (IOException e) {
//                        Log.i(TAG,"startClient  close IOException "+e.getMessage());
//                        e.printStackTrace();
//                    }
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
