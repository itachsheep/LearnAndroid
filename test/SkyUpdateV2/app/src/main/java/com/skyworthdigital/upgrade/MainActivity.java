package com.skyworthdigital.upgrade;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.skyworthdigital.upgrade.port.IUpgrade;
import com.skyworthdigital.upgrade.service.UpgradeService;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.LogUtil;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {
    private IUpgrade upgradeService = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            LogUtil.log("connect service");
            upgradeService = IUpgrade.Stub.asInterface(service);
            try {
                upgradeService.startCheckUpgrade();
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            LogUtil.log("disconnect service");
            upgradeService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent in = new Intent(getApplicationContext(), UpgradeService.class);
        bindService(in, mConnection, Context.BIND_AUTO_CREATE);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPLOAD_DEVICE_INFO, 0));
    }

}
