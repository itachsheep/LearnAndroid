package com.java.eventbusdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.java.eventbusdemo.R;
import com.java.eventbusdemo.common.App;
import com.java.eventbusdemo.message.MessageEvent;
import com.java.eventbusdemo.utils.LogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SDT14324 on 2017/9/19.
 */

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
    }

    private MessageEvent ms = new MessageEvent("1111");

    @OnClick(R.id.ac_post_tv)
    public void sendMessage(){
       /* String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();
        LogUtils.i("PostActivity.sendMessage, threadName : "+threadName+", threadId: "+threadId);
        App.eventBus.post(new ThreadDetailBean(threadName,threadId));*/

//        App.eventBus.postSticky(ms);
        App.eventBus.post(ms);
    }

    @OnClick(R.id.ac_post_ch)
    public void change(){
//        ms = new MessageEvent("2222");
        ms.message = "333333";
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.eventBus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING,priority = 100)
    public void onMessage(MessageEvent event){
        LogUtils.i("PostActivity.onMessage event : "+event.message+", priority: 100");
        App.eventBus.cancelEventDelivery(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.eventBus.unregister(this);
    }
}
