package com.taow.threadwaitlock;

import android.app.Clog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SmallHandler sHandler;//子线程中的的handler
    private long count = 0;

    private Object lockObj = new Object();
    private Button btStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStart = (Button) findViewById(R.id.bt_start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallThread thread = new SmallThread();
                thread.start();
            }
        });
    }

    class SmallHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            count++;
            Clog.i("handlemessage count: "+count);
            sHandler.sendEmptyMessageDelayed(1,1000);
            if(count == 6){
                synchronized (new Object()) {
                    //当计数达到6，则让线程等待，这个时候启动计时器，5s后让线程唤醒
                    openTimer();
                    waitThread();
                }
            }else if( count == 10){
                //当计数达到10，则让线程睡眠
                synchronized (new Object()){
                    sleepSomeTime();
                }
            }
        }
    }
    private void sleepSomeTime(){
        Clog.i("sleepSomeTime ");
        try {
            Clog.i("### sleepSomeTime 2 mins");
            Thread.sleep(5* 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void openTimer(){
        Clog.i("openTimer ");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //唤醒线程
                Clog.i("openTimer notify to..");
                notifySmallThread();
            }
        },5*1000);
    }
    private void notifySmallThread(){
        Clog.i("#### notifySmallThread ");
        synchronized (lockObj){
            Clog.i("####  notifySmallThread notify to ..");
//            lockObj.notifyAll();
            lockObj.notify();
        }
    }
    private void waitThread(){
        Clog.i("#### waitThread ");
        synchronized (lockObj){
            try {
                lockObj.wait();
            } catch (InterruptedException e) {
                Clog.i("##### waitThread error "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    class SmallThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            sHandler = new SmallHandler();
            Clog.i("small thread run .."+count);
            sHandler.sendEmptyMessageDelayed(1,1000);
            Looper.loop();
        }
    }
}
