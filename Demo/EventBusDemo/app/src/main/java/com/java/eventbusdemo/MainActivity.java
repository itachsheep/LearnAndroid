package com.java.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.java.eventbusdemo.activity.PostActivity;
import com.java.eventbusdemo.bean.ThreadDetailBean;
import com.java.eventbusdemo.common.App;
import com.java.eventbusdemo.message.MessageEvent;
import com.java.eventbusdemo.utils.LogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.eventBus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessagePost(ThreadDetailBean td){
        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();
        LogUtils.i("MainActivity.onMessage post, theadName ==  "+(threadName.equals(td.getThreadName()))+
                ", threadId == "+(threadId == td.getThreadId()));
        LogUtils.i("MainActivity.onMessage post, theadName: "+threadName+", theadId: "+threadId);
    }

    /**
     * EventBus uses a single background thread that will deliver all its events sequentially.
     * Event handlers using this mode should try to return quickly to avoid blocking the background thread.
     * @param td
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageBground(ThreadDetailBean td){
        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();
        LogUtils.i("MainActivity.onMessage bgroud, theadName ==  "+(threadName.equals(td.getThreadName()))+
                ", threadId == "+(threadId == td.getThreadId()));
        LogUtils.i("MainActivity.onMessage bgroud, theadName: "+threadName+", theadId: "+threadId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeessageMain(ThreadDetailBean td){
        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();
        LogUtils.i("MainActivity.onMessage main, theadName ==  "+(threadName.equals(td.getThreadName()))+
                ", threadId == "+(threadId == td.getThreadId()));
        LogUtils.i("MainActivity.onMessage main, theadName: "+threadName+", theadId: "+threadId);
    }

    /**
     * Event handler methods should use this mode if their execution might take some time, e.g. for network access.
     * Avoid triggering a large number of long running asynchronous handler methods at the same time to limit the number of concurrent threads.
     * EventBus uses a thread pool to efficiently reuse threads from completed asynchronous event handler notifications.
     * @param td
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageAsync(ThreadDetailBean td){
        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();
        LogUtils.i("MainActivity.onMessage async, theadName ==  "+(threadName.equals(td.getThreadName()))+
                ", threadId == "+(threadId == td.getThreadId()));
        LogUtils.i("MainActivity.onMessage async, theadName: "+threadName+", theadId: "+threadId);
    }

    @Subscribe(threadMode = ThreadMode.POSTING,priority = 50)
    public void onMessage(MessageEvent event){

        LogUtils.i("MainActivity.onMessage event: "+event.message+", priority: 50");
    }




    @OnClick(R.id.main_tv)
    public void onclick(){
        LogUtils.i("MainActivity.onclick ");
        startActivity(new Intent(MainActivity.this, PostActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.eventBus.unregister(this);
    }
}
