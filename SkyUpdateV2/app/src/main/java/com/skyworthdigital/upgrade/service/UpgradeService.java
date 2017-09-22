package com.skyworthdigital.upgrade.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;

import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.db.TaskManager;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.port.IUpgrade;
import com.skyworthdigital.upgrade.port.UpgradeCallback;
import com.skyworthdigital.upgrade.port.UpgradeInfo;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class UpgradeService extends Service {

	public final RemoteCallbackList<UpgradeCallback> mCallbacks = new RemoteCallbackList<UpgradeCallback>();
	
    private final IUpgrade.Stub upgradeBinder = new IUpgrade.Stub() {

        @Override
        public void startCheckUpgrade() throws RemoteException {
            LogUtil.log("startCheckUpgrade");
            EventBus.getDefault().post(
                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_TRIGGER, MessageEvent.FROM_USER_MANUAL));
        }

        @Override
        public void installPackage() throws RemoteException {
            EventBus.getDefault().post(
                MessageEvent.createMsg(MessageEvent.EVENT_USER_INSURE_RECOVERY, MessageEvent.FROM_USER_MANUAL));
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
        public int getDownloadProcess() throws RemoteException {
            UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
            if (task != null) {
                return task.getDownloadProcess();
            }
            return 0;
        }

		@Override
		public boolean hasNewVersion() throws RemoteException {
			// TODO Auto-generated method stub
			boolean findNewVersion = false;
			UpgradeTask task = TaskManager.getTaskManager().obtainLastTask();
			if(task != null) {
				if(!TextUtils.isEmpty(task.getPkgversion()) && !CommonUtil.getUpgradeResult(task)) {
					findNewVersion = true;
				}
			}
			return findNewVersion;
		}

		@Override
		public void registerCallback(UpgradeCallback cb) throws RemoteException {
			// TODO Auto-generated method stub
			if (cb != null) {
				mCallbacks.register(cb);
			}
		}

		@Override
		public void unregisterCallback(UpgradeCallback cb)
				throws RemoteException {
			// TODO Auto-generated method stub
			if (cb != null) {
				LogUtil.log("ungregister");
//				if (mNm != null) {
//					mNm.cancel(DOWNLOAD_SERVICE_STATUS);
//				}
				mCallbacks.unregister(cb);
			}
		}
        
    };

    private static boolean FROM_WAKE_UP = false;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	String action = intent.getAction();
            LogUtil.log("receive broadcast : " + action);
            if (action.equalsIgnoreCase(Intent.ACTION_SCREEN_ON)) {
                // 在待机启机以后，延迟30秒启动
            	if(FROM_WAKE_UP) {
            		 new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             startCheckVersion(MessageEvent.FROM_WAKE_UP);
                         }
                     }, 10000);
            		 FROM_WAKE_UP = false;
            	}
               

            }else if (action.equalsIgnoreCase(Intent.ACTION_SCREEN_OFF)) {
            	FROM_WAKE_UP = true;
			}
        }
    };

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return upgradeBinder;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        EventBus.getDefault().register(this);
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
        
        startAutoCheckPlanThread();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private static boolean timeVerify = false;
    private CheckTimeThread checkThread = null;
    private void startAutoCheckPlanThread() {
    	checkThread = new CheckTimeThread();
		checkThread.setPriority(Thread.MAX_PRIORITY);
		checkThread.start();
    }
    
    class CheckTimeThread extends Thread {
		@Override
		public void run() {
			Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
			LogUtil.log("start time verify!");
			while (true) {
				long currTime = System.currentTimeMillis();
				timeVerify = CommonUtil.getTimeCheck(currTime);

				if (timeVerify) {
					setAutoCheckVersionPlan();
					return;
				}

				LogUtil.log("time verify is false, sleep 30s");

				try {
					Thread.sleep(30000);
				} catch (Exception e) {
					LogUtil.log("Exception : " + e.getMessage());
				}
			}
		}
	}
    
    private void setAutoCheckVersionPlan() {
        LogUtil.log("create auto check plan! ");

        AlarmManager am =
            (AlarmManager) UpgradeApp.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent setAlertIntent = new Intent(UpgradeApp.getInstance().getApplicationContext(), UpgradeReceiver.class);
        setAlertIntent.setAction(CommonUtil.ACTION_AUTO_CHECK);

        PendingIntent mAlarmSender =
            PendingIntent.getBroadcast(
                UpgradeApp.getInstance().getApplicationContext(),
                0,
                setAlertIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        long period = CommonUtil.DAY_LONG;
        // long firstTime = SystemClock.elapsedRealtime() + period;
        long firstTime = System.currentTimeMillis() + period;

        LogUtil.log("first time : " + firstTime + " - period : " + period);
        // 每隔period小时去启动AutoCheckService
        am.setRepeating(AlarmManager.RTC, firstTime, period, mAlarmSender);

    }

    private void startCheckVersion(int entry) {
        LogUtil.log("startCheckVersion");
        EventBus.getDefault().post(
            MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_TRIGGER, entry));
    }

    

    @Subscribe
    public void onEventBackgroundThread(MessageEvent event) {
        
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
				LogUtil.log("RemoteException " + e);
			}

			mCallbacks.finishBroadcast();
		}
    }
    
}
