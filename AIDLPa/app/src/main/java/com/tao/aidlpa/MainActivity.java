package com.tao.aidlpa;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.skyworthauto.speak.remote.CmdInfo;
import com.skyworthauto.speak.remote.IRemote;
import com.skyworthauto.speak.remote.IService;
import com.skyworthauto.speak.remote.RemoteSpeak;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String TAG = "SkySpeak.on.MainActivity";
    private final int GLOBAL_CMD_ID = 100;
    private final int CUSTOM_CMD_ID = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bind(View view){
        RemoteSpeak.getInstance().bindService(MainActivity.this, new IService() {
            @Override
            public void onServiceConnected() {
                Log.d(TAG, "onServiceConnected  ");
                RemoteSpeak.getInstance().registerGlobalCmd(MainActivity.this,mGlobalCmd);
                RemoteSpeak.getInstance().registerCustomCmd(MainActivity.this,mCustomCmd);
            }
        });
    }

    public void unbind(View view){
        Log.d(TAG, "unbind click  ");
        RemoteSpeak.getInstance().unBindService(MainActivity.this);
    }



    public void regGlobalCmd(View view){
        RemoteSpeak.getInstance().registerGlobalCmd(MainActivity.this,mGlobalCmd);
    }

    public void unRegGlobalCmd(View view){
        RemoteSpeak.getInstance().unRegisterGlobalCmd(MainActivity.this,mGlobalCmd);
    }

    public void regCustomCmd(View view){
        RemoteSpeak.getInstance().registerCustomCmd(MainActivity.this,mCustomCmd);
    }

    public void unRegCustomCmd(View view){
        RemoteSpeak.getInstance().unRegisterCustomCmd(MainActivity.this,mCustomCmd);
    }


    @Override
    protected void onStop() {
        super.onStop();
        RemoteSpeak.getInstance().unBindService(MainActivity.this);
    }

    IRemote mGlobalCmd = new IRemote() {
        @Override
        public List<CmdInfo> getList() throws RemoteException {
            List<CmdInfo> list = new ArrayList<>();
            String key = "open_document";
            String[] array = {"打开文档","打开我的文档"};
            CmdInfo info = new CmdInfo(key,array);
            list.add(info);
            return list;
        }

        @Override
        public int getId() throws RemoteException {
            return GLOBAL_CMD_ID;
        }


        @Override
        public void onCommand(String cmdKey, String cmdData) throws RemoteException {
            Log.d(TAG,"自己处理全局指令 cmdKey: "+cmdKey+", cmdData: "+cmdData);
        }
    };
    IRemote mCustomCmd = new IRemote(){
        @Override
        public List<CmdInfo> getList() throws RemoteException {
            List<CmdInfo> list = new ArrayList<>();
            String key = "shopping_something";
            String[] array = {"打开我的手机","打开手机"};
            CmdInfo info = new CmdInfo(key,array);
            list.add(info);
            return list;
        }

        @Override
        public int getId() throws RemoteException {
            return CUSTOM_CMD_ID;
        }

        @Override
        public void onCommand(String cmdKey, String cmdData) throws RemoteException {
            Log.d(TAG,"自己处理语音指令 cmdKey: "+cmdKey+", cmdData: "+cmdData);
        }
    };

    /*public void stop(View view){
        Log.d(TAG, "stop click  ");
        Intent intent = new Intent();
        ComponentName component = new ComponentName("com.skyworthauto.speak","com.skyworthauto.speak.SpeakService");
        intent.setComponent(component);
        stopService(intent);
    }*/
}
