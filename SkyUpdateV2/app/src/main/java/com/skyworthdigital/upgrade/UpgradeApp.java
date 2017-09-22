package com.skyworthdigital.upgrade;

import org.xutils.x;

import android.app.Application;

import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.device.DeviceInfoController;
import com.skyworthdigital.upgrade.state.StateManager;
import com.skyworthdigital.upgrade.util.Config;
import com.skyworthdigital.upgrade.util.SkyUploadFlowManager;

public class UpgradeApp extends Application {
    private static UpgradeApp instance = null;

    private StateManager stateManager;
    private TaskManager taskManager;
    private DeviceInfoController deviceInfoController;
    private Config config;

    public void onCreate() {
        super.onCreate();
        instance = this;
        config = new Config();
        x.Ext.init(this);
        stateManager = new StateManager();
        taskManager = new TaskManager();
        deviceInfoController = new DeviceInfoController();
        
        SkyUploadFlowManager.getInstance().initUploadAgent(this);
    }

    public static UpgradeApp getInstance() {
        return instance;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public DeviceInfoController getDeviceInfoController() {
        return deviceInfoController;
    }

    public Config getConfig() {
        return config;
    }

}
