package tao.com.memory.activity;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import andorid.taow.selfview.IDownload;
import andorid.taow.selfview.IDownloadCallback;
import tao.com.memory.R;
import tao.com.memory.utils.LogUtil;

/**
 * Created by taow on 2017/6/5.
 */

public class CheckUpgradeActivity extends Activity implements View.OnClickListener {
    private Button btStart;
    private Button btWait;
    private Button btNotify;
    private Button btSleep;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_upgrade);
        btStart = (Button) findViewById(R.id.check_bt_start);
        btWait = (Button) findViewById(R.id.check_bt_wait);
        btNotify = (Button) findViewById(R.id.check_bt_notify);
        btSleep = (Button) findViewById(R.id.check_bt_sleep);
        pb = (ProgressBar) findViewById(R.id.check_pb);
        btStart.setOnClickListener(this);
        btWait.setOnClickListener(this);
        btNotify.setOnClickListener(this);
        btSleep.setOnClickListener(this);
        IntentService service = new IntentService("dd") {
            @Override
            public void onCreate() {
                super.onCreate();
            }

            @Override
            protected void onHandleIntent(Intent intent) {

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check_bt_start:
                bindDownloadService();
                break;
            case R.id.check_bt_wait:
                try {
                    downloadBinder.operateIpService(0);
//                    downloadBinder.setIpUpgradeCallback(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.check_bt_notify:
                try {
                    downloadBinder.operateIpService(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.check_bt_sleep:
                try {
                    downloadBinder.operateIpService(2);
//                    downloadBinder.setIpUpgradeCallback(null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
    private IDownload downloadBinder;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.i("CheckUpgradeActivity.onServiceConnected..");
            downloadBinder = IDownload.Stub.asInterface(iBinder);
            initDownloadCallback();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.i("CheckUpgradeActivity.onServiceDisconnected..");
            downloadBinder = null;
        }
    };

    private void initDownloadCallback() {
        if(null != downloadBinder){
            try {
                downloadBinder.setIpUpgradeCallback(new IDownloadCallback.Stub(){

                    @Override
                    public void onResult(int state, int progress) throws RemoteException {
                        LogUtil.i("CheckUpgradeActivity.downlaod state:"+state+", progress: "+progress);
                        pb.setProgress(progress);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindDownloadService() {
        Intent intent = new Intent();
        intent.setAction("andorid.taow.selfview.download.service");
        intent.setPackage("andorid.taow.selfview");
        bindService(intent,serviceConn, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("CheckUpgradeActivity.onDestroy..");
        unbindService(serviceConn);
        if(null != downloadBinder){
            downloadBinder = null;
        }
        if(null != serviceConn){
            serviceConn = null;
        }
    }

    @Override
    protected void onDestroy() {
        LogUtil.i("CheckUpgradeActivity.onDestroy..");
        super.onDestroy();

    }
}
