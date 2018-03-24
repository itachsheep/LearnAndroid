package com.skyworthdigital.upgrade.state;

import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.utils.LogUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class StateManager {
    private StateProcess currProcess = null;


    public StateManager() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onEventBackgroundThread(MessageEvent event){
        LogUtil.i("StateManager.onEventBackgroundThread : " + event.toString());
        int from = event.getFrom();
        switch (event.getWhat()) {
            case MessageEvent.EVENT_UPGRADE_TRIGGER :
                if (from == MessageEvent.FROM_USER_MANUAL) {
                    checkNewVersion(true);
                }
                else {
                    checkNewVersion(false);
                }

                break;
            case MessageEvent.EVENT_UPGRADE_NEXT :
                processMessage();
                break;
            case MessageEvent.EVENT_STOP_CURR_TASK :
                stopCurrProcess();
                break;
            case MessageEvent.EVENT_USER_INSURE_RECOVERY :
                processUserInsureRecovery();
                break;
            case MessageEvent.EVENT_UPLOAD_DEVICE_INFO :
                UpgradeApp.getInstance().getDeviceInfoController().uploadDeviceInfo();
                break;
        }
    }

    private void processUserInsureRecovery() {
        UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
        task.setUserInsureReboot(true);
        TaskManager.getTaskManager().updateObj(task);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
    }

    private void stopCurrProcess() {
        LogUtil.i("processMessage currProcess : " + currProcess);
        if (currProcess != null) {
            currProcess.stopProcess();
        }
    }

    public void processMessage() {
        synchronized (StateManager.this) {
            LogUtil.i("processMessage currProcess : " + currProcess);
            if (currProcess != null && currProcess.isRunning()) {
                LogUtil.i("currProcess is running, status : " + currProcess.getStatus());
                return;
            }

            UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();

            currProcess = StateProcessFactory.createProcess(task);
            currProcess.startProcess();
        }
    }

    public void checkNewVersion(boolean manual) {
        LogUtil.i("checkNewVersion manual : " + manual);
        StateProcess checkprocess = StateProcessFactory.createCheckProcess();
        UpgradeTask task = new UpgradeTask();
        task.setManual(manual);
        checkprocess.setTask(task);
        checkprocess.startProcess();
    }
}
