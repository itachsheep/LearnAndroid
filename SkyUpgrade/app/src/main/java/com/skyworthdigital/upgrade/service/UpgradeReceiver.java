package com.skyworthdigital.upgrade.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Process;

import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class UpgradeReceiver extends BroadcastReceiver {
    private UploadAndCheckThread checkThread = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.i("UpgradeReceiver.receive action:" + action);

        if (action.equals(Intent.ACTION_BOOT_COMPLETED) || action.equals("com.skyworth.test.br")) {
            LogUtil.i("UpgradeReceiver.start UpgradeService");
            CommonUtil.setHasReceviedBootCompleted(true);
            startUploadAndCheckThread();
            context.startService(new Intent(context, UpgradeService.class));
        }else if(action.equals(IConstants.ACTION_AUTO_CHECK)){
            startCheckVersion(MessageEvent.FROM_AUTO_CHECK);
        }else if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            if(!CommonUtil.isHasReceviedBootCompleted()) {
                //not receive boot, will return
                return;
            }

            boolean isConnect = CommonUtil.getNetworkConnect();
            if(isConnect && !CommonUtil.isNetworkConnectState()){
                //network from disconnect to connect, exclude wireless or ethernet swtich cause network state change
                if (CommonUtil.isFirstReceiveNetChange()) {
                    //first bootup, network will change too
                    CommonUtil.setNotFirstReceiveNetChange();
                    CommonUtil.setNetworkConnectState(isConnect);
                    return;
                }
                LogUtil.i("UpgradeReceiver network change, check version");
                startCheckVersion(MessageEvent.FROM_NET_CONNECT);
            }
            CommonUtil.setNetworkConnectState(isConnect);

        }

    }

    private void startUploadAndCheckThread() {
        checkThread = new UploadAndCheckThread();
        checkThread.setPriority(Thread.MAX_PRIORITY);
        checkThread.start();
    }

    class UploadAndCheckThread extends Thread{
        @Override
        public void run() {
            synchronized (UpgradeReceiver.this){
                Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

                LogUtil.i("after power up, sleep 1 min and then upload device info");


               /* new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                    public void run() {
                        startUploadDeviceInfo();
                    }

//                }, IConstants.SECONDS_60);
                }, IConstants.SECONDS_5);*/

               /* new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                    public void run() {
                        startUploadUpgradeInfo();
                    }

                }, IConstants.SECONDS_90);*/


                LogUtil.i("after upload device, sleep 2 min and then check new version");
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startCheckVersion(MessageEvent.FROM_BOOT);
                    }
//                },IConstants.SECONDS_120);
                },IConstants.SECONDS_5);
            }
        }
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

    private void startCheckVersion(int entry) {
        LogUtil.i("startCheckVersion entry: "+entry);
        EventBus.getDefault().post(
                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_TRIGGER, entry));
    }
}
