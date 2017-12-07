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

import com.skyworthauto.speak.CmdInfo;
import com.skyworthauto.speak.ISpeak;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent("111");
//        bindService(intent,conn, Context.BIND_AUTO_CREATE);


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
                CmdInfo cmdInfo = new CmdInfo("11","22");
                cmdInfo.setCmdKey("cmdKey!!");

                try {
                    Log.d(TAG," onclick bt_call");
                    iSpeak.registerCustomCmd(cmdInfo);
                    iSpeak.registerGlobalCmd(cmdInfo);
                    iSpeak.unRegisterCmd(cmdInfo);
                } catch (RemoteException e) {
                    Log.d(TAG,"RemoteException : "+e.getLocalizedMessage(),e);
                    e.printStackTrace();
                }
            }
        });
    }
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
