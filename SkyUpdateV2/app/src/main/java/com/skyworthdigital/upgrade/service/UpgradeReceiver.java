package com.skyworthdigital.upgrade.service;

import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;

import de.greenrobot.event.EventBus;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Process;

public class UpgradeReceiver extends BroadcastReceiver {

    private uploadAndCheckThread checkThread = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.log("ota receive action:" + action);

        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            LogUtil.log("start UpgradeService");
            CommonUtil.setHasReceviedBootCompleted(true);
            startUploadAndCheckThread();
            context.startService(new Intent(context, UpgradeService.class));
        }
        else if (action.equals(CommonUtil.ACTION_AUTO_CHECK)) {
            startCheckVersion(MessageEvent.FROM_AUTO_CHECK);
        }else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
        	
        	if(!CommonUtil.isHasReceviedBootCompleted()) {
        		return;
        	}
        	
			boolean conn = CommonUtil.getNetworkConnect();
			if (!CommonUtil.isNetworkConnectState() && conn) {
				if (CommonUtil.isFirstReceiveNetChange()) {
					CommonUtil.setNotFirstReceiveNetChange();
					CommonUtil.setNetworkConnectState(conn);
					return;
				}
				startCheckVersion(MessageEvent.FROM_NET_CONNECT);
			}
			CommonUtil.setNetworkConnectState(conn);
		}
    }
    
    private void startUploadAndCheckThread() {
        checkThread = new uploadAndCheckThread();
        checkThread.setPriority(Thread.MAX_PRIORITY);
        checkThread.start();
    }

    class uploadAndCheckThread extends Thread {
        @Override
        public void run() {
            synchronized (this) {
                Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

                LogUtil.log("after power up, sleep 1 min and then upload device info");
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

					public void run() {
						startUploadDeviceInfo();
					}

				}, 60000);
                
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

					public void run() {
						startUploadUpgradeInfo();
					}

				}, 90000);
                
                LogUtil.log("after upload device, sleep 1 min and then check new version");
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

					public void run() {
						 startCheckVersion(MessageEvent.FROM_BOOT);
					}

				}, 120000);
            }
        }
    }

    private void startCheckVersion(int entry) {
        LogUtil.log("startCheckVersion");
        EventBus.getDefault().post(
            MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_TRIGGER, entry));
    }

    private void startUploadDeviceInfo() {
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPLOAD_DEVICE_INFO, 0));
    }
    
    private void startUploadUpgradeInfo() {
    	UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
    	
    	if(null == task) {
    		return;
    	}
    	
    	if(task.getState() == StateProcess.STATE_RECOVERY && task.isWriteRecoveryFlag()) {
    		EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
    	}
    }
    
}
