package com.tao.handlerlearndemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟在子线程new handler
                new MyThread().start();

                //杀死自己
                /*System.out.println("### process pid ### pid: "+ Process.myPid());
                Process.killProcess(Process.myPid());*/



            }
        });





    }



    class MyThread extends Thread{

        @Override
        public void run() {

//            Looper.prepare();


            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what == 1){
                        System.out.println("### handle message ### msg:"+msg);
                    }
                }
            };

            System.out.println("### run ###");
           // handler.sendEmptyMessage(1);

//            Looper.loop();
        }
    }
}
