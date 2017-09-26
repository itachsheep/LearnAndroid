package com.xutil.xutildemo;

import android.app.Application;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class App extends Application {
    private static App instance;
    private TaskManager taskManager;
    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        taskManager = new TaskManager();
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
