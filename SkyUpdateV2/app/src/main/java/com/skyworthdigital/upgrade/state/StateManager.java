package com.skyworthdigital.upgrade.state;

import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.LogUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class StateManager {
    private StateProcess currProcess = null;

    public StateManager() {
        EventBus.getDefault().register(this);
    }

	public void processMessage() {
		synchronized (this) {
			LogUtil.log("processMessage currProcess : " + currProcess);
			if (currProcess != null && currProcess.isRunning()) {
				LogUtil.log("currProcess status : " + currProcess.getStatus());
				return;
			}
			UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();

			currProcess = StateProcessFactory.createProcess(task);
			currProcess.startProcess();
		}
	}

    public void checkNewVersion(boolean manual) {
        LogUtil.log("checkNewVersion manual : " + manual);
        StateProcess checkprocess = StateProcessFactory.createCheckProcess();
        UpgradeTask task = new UpgradeTask();
        task.setManual(manual);
        checkprocess.setTask(task);
        checkprocess.startProcess();
    }

    private void stopCurrProcess() {
        LogUtil.log("processMessage currProcess : " + currProcess);
        if (currProcess != null) {
            currProcess.stopProcess();
        }
    }

    private void processUserInsureRecovery() {
        UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
        task.setUserInsureReboot(true);
        TaskManager.getTaskManager().updateObj(task);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
    }

    @Subscribe
    public void onEventBackgroundThread(MessageEvent event) {
        LogUtil.log("onEventBackgroundThread : " + event.toString());
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

}
