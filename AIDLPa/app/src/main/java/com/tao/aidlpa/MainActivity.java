package com.tao.aidlpa;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.skyworthauto.speak.remote.CmdInfo;
import com.skyworthauto.speak.remote.IRemoteCmd;
import com.skyworthauto.speak.remote.ISpeak;
import com.skyworthauto.speak.remote.RemoteSpeak;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String TAG = "SkySpeak.on.MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent("111");
//        bindService(intent,conn, Context.BIND_AUTO_CREATE);

        RemoteSpeak.getInstance().init(MainActivity.this);
        findViewById(R.id.bt_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG," onclick bt_bind");
                Intent intent = new Intent();

                ComponentName componentName = new ComponentName("com.skyworthauto.speak",
                        "com.skyworthauto.speak.SpeakService");
                intent.setComponent(componentName);
                bindService(intent,conn, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.bt_call).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG," onclick bt_call");
                try{
                    RemoteSpeak.getInstance().registerGlobalCmd(remoteCmd,MainActivity.this);
                }catch(Exception e){
                    Log.d(TAG, "onclick error: "+e.getMessage(),e);
                }

            }
        });
    }

    IRemoteCmd remoteCmd = new IRemoteCmd.Stub() {
        @Override
        public List<CmdInfo> getList() throws RemoteException {
            List<CmdInfo> list = new ArrayList<>();
            String key = "open_ppt";
            String[] array = {"打开文档","打开我的文档"};
            CmdInfo info = new CmdInfo(key,array);
            list.add(info);
            return list;
        }

        @Override
        public String getId() throws RemoteException {
            return getPackageName();
        }


        @Override
        public void onCommand(String cmdKey, String cmdData) throws RemoteException {
            Log.d(TAG," onCommand cmdKey: "+cmdKey+", cmdData: "+cmdData);
        }
    };
    private ISpeak iSpeak;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSpeak = ISpeak.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    /*ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyTest iMyTest = IMyTest.Stub.asInterface(service);
            try {
                iMyTest.testBinder();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };*/


}
