package com.skyworthdigital.upgrade.common;

import android.app.Application;

import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.state.StateManager;
import com.skyworthdigital.upgrade.upload.DeviceInfoController;
import com.skyworthdigital.upgrade.utils.Config;

import org.xutils.x;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class UpgradeApp extends Application {
    private static UpgradeApp instance = null;
    private StateManager stateManager;
    private TaskManager taskManager;
    private Config config;
    private DeviceInfoController deviceInfoController;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        stateManager = new StateManager();
        taskManager = new TaskManager();
        config = new Config();
        x.Ext.init(this);
        deviceInfoController = new DeviceInfoController();
//        SkyUploadFlowManager.getInstance().initUploadAgent(this);
    }

    public static UpgradeApp getInstance() {
        return instance;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public Config getConfig() {
        return config;
    }

    public DeviceInfoController getDeviceInfoController() {
        return deviceInfoController;
    }
}
