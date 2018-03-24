package com.skyworthdigital.upgrade.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.port.IUpgrade;
import com.skyworthdigital.upgrade.port.UpgradeCallback;
import com.skyworthdigital.upgrade.port.UpgradeInfo;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class UpgradeService extends Service {
    private CheckTimeThread checkThread = null;
    private static boolean timeVerify = false;
    public final RemoteCallbackList<UpgradeCallback> mCallbacks = new RemoteCallbackList<UpgradeCallback>();
    private final IUpgrade.Stub upgradeBinder = new IUpgrade.Stub() {

        @Override
        public void registerCallback(UpgradeCallback cb) throws RemoteException {
            if (cb != null) {
                LogUtil.i("registerCallback ");
                mCallbacks.register(cb);
            }
        }

        @Override
        public void unregisterCallback(UpgradeCallback cb) throws RemoteException {
            if (cb != null) {
                LogUtil.i("unregisterCallback");
                mCallbacks.unregister(cb);
            }
        }

        @Override
        public void startCheckUpgrade() throws RemoteException {
            LogUtil.i("startCheckUpgrade");
            EventBus.getDefault().post(
                    MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_TRIGGER, MessageEvent.FROM_USER_MANUAL));
        }

        @Override
        public UpgradeInfo getUpgradeInfo() throws RemoteException {
            UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
            if (task != null) {
                return task.getUpgradeInfo();
            }
            return null;
        }

        @Override
        public void installPackage() throws RemoteException {
            EventBus.getDefault().post(
                    MessageEvent.createMsg(MessageEvent.EVENT_USER_INSURE_RECOVERY, MessageEvent.FROM_USER_MANUAL));
        }

        @Override
        public int getDownloadProcess() throws RemoteException {
            UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
            if (task != null) {
                return task.getDownloadProcess();
            }
            return 0;
        }

        @Override
        public boolean hasNewVersion() throws RemoteException {
            boolean findNewVersion = false;
            UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
            if(task != null) {
                if(!TextUtils.isEmpty(task.getPkgversion()) && !CommonUtil.getUpgradeResult(task)) {
                    findNewVersion = true;
                }
            }
            return findNewVersion;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return upgradeBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);

        // TODO: 2017/9/25 屏幕灭、屏幕亮监听

        startAutoCheckPlanThread();
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onEventBackgroundThread(MessageEvent event){
        final int callbacks = mCallbacks.beginBroadcast();
        if (callbacks > 0) {
            UpgradeCallback item = mCallbacks.getBroadcastItem(0);
            try {
                switch (event.getWhat()) {
                    case MessageEvent.MSG_DOWNLOAD_PROGRESS :
                        int progress = event.getParam();
                        item.downloadProgress(progress);
                        break;
                    case MessageEvent.MSG_DOWNLOAD_FAILED :
                        int errCode = event.getParam();
                        item.upgradeErrorCode(errCode);
                        break;
                    case MessageEvent.MSG_CHECK_NEW_VERSION :
                        int code = event.getParam();
                        item.checkVersionResult(code);
                        break;
                }
            } catch (RemoteException e) {
                LogUtil.i("RemoteException " + e);
            }

            mCallbacks.finishBroadcast();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void startAutoCheckPlanThread() {
        checkThread = new CheckTimeThread();
        checkThread.setPriority(Thread.MAX_PRIORITY);
        checkThread.start();
    }

    private void setAutoCheckVersionPlan() {
        LogUtil.i("setAutoCheckVersionPlan auto check plan! ");
        AlarmManager am =
                (AlarmManager) UpgradeApp.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent setAlertIntent = new Intent(UpgradeApp.getInstance().getApplicationContext(), UpgradeReceiver.class);
        setAlertIntent.setAction(IConstants.ACTION_AUTO_CHECK);

        PendingIntent mAlarmSender =
                PendingIntent.getBroadcast(
                        UpgradeApp.getInstance().getApplicationContext(),
                        0,
                        setAlertIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

        long period = IConstants.DAY_LONG;
        // long firstTime = SystemClock.elapsedRealtime() + period;
        long firstTime = System.currentTimeMillis() + period;

        LogUtil.i("first time : " + firstTime + " - period : " + period);
        // 每隔period小时去启动AutoCheckService
        am.setRepeating(AlarmManager.RTC, firstTime, period, mAlarmSender);
    }

    class CheckTimeThread extends Thread {
        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
            LogUtil.i("start time verify!");
            while (true) {
                long currTime = System.currentTimeMillis();
                timeVerify = CommonUtil.getTimeCheck(currTime);

                if (timeVerify) {
                    setAutoCheckVersionPlan();
                    return;
                }

                LogUtil.i("time verify is false, sleep 30s");

                try {
                    Thread.sleep(30000);
                } catch (Exception e) {
                    LogUtil.i("Exception : " + e.getMessage());
                }
            }
        }
    }
}
