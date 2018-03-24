package com.xutil.xutildemo.upgradeprogress;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.skyworthdigital.upgrade.port.IUpgrade;
import com.skyworthdigital.upgrade.port.UpgradeCallback;
import com.xutil.xutildemo.R;
import com.xutil.xutildemo.utils.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by SDT14324 on 2017/10/12.
 */

@ContentView(R.layout.activity_upgrade)
public class UpgradeProgressActivity extends Activity {

    @ViewInject(R.id.ac_up_bt)
    private Button btShowPb;

    @ViewInject(R.id.ac_up_pb)
    private ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(R.id.ac_up_bt)
    private void onBtShowPb(View view){
        Intent  intent = new Intent("skyworthdigital.intent.action.Upgrade_SERVICE");
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }


    private IUpgrade iUpgrade;
    private ServiceConnection serviceConnection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iUpgrade = IUpgrade.Stub.asInterface(iBinder);
            try {
                iUpgrade.registerCallback(upgradeCallback);
            } catch (RemoteException e) {
                LogUtil.i("onServiceConnected registerCallback error: "+e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentNe) {
            try {
                iUpgrade.unregisterCallback(upgradeCallback);
            } catch (RemoteException e) {
                LogUtil.i("onServiceDisconnected unregisterCallback error: "+e.getMessage());
                e.printStackTrace();
            }
        }
    };

    private UpgradeCallback.Stub upgradeCallback = new UpgradeCallback.Stub() {
        @Override
        public void downloadProgress(int progress) throws RemoteException {
            LogUtil.i("UpgradeCallback downloadProgress process: "+progress);
            pb.setProgress(progress);
            btShowPb.setText("升级进度："+progress+"%");
        }

        @Override
        public void upgradeErrorCode(int errorCode) throws RemoteException {
            LogUtil.i("UpgradeCallback upgradeErrorCode errorCode: "+errorCode);
        }

        @Override
        public void checkVersionResult(int hasNewVersion) throws RemoteException {
            LogUtil.i("UpgradeCallback checkVersionResult hasNewVersion: "+hasNewVersion);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
