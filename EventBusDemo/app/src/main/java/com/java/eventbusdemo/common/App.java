package com.java.eventbusdemo.common;

import android.app.Application;
import android.support.compat.BuildConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SDT14324 on 2017/9/19.
 */

public class App extends Application {
    public static EventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(BuildConfig.DEBUG)// fail when a subscriber throws an exception
                .build();

    }
}
