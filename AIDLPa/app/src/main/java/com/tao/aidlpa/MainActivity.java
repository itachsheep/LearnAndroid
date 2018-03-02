package com.tao.aidlpa;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skyworthauto.speak.remote.CmdInfo;
import com.skyworthauto.speak.remote.ICmdAM;
import com.skyworthauto.speak.remote.ICmdFM;
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
        findViewById(R.id.bt_bind).requestFocus();
        Log.d(TAG, "onCreate");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        Log.i(TAG,""+Thread.currentThread().getName()+", id = "+
                Thread.currentThread().getId()+","+android.os.Process.myPid( )
        +android.os.Process.myUid());
    }
    private static final int AM_FROM = 522;
    private static final int AM_TO = 1620;

    private static final float FM_FROM = 87.5f;
    private static final float FM_TO = 108.0f;
    public void bind(View view){
        Toast.makeText(MainActivity.this,"绑定服务",Toast.LENGTH_SHORT).show();
        RemoteSpeak.getInstance().bindService(MainActivity.this, new IService() {
            @Override
            public void onServiceConnected() {
                Log.d(TAG, "onServiceConnected  ");
                //注册全局指令
                RemoteSpeak.getInstance().registerGlobalCmd(MainActivity.this,mGlobalCmd);
                //注册唤醒指令
                RemoteSpeak.getInstance().registerCustomCmd(MainActivity.this,mCustomCmd);
                //注册调幅指令am
                RemoteSpeak.getInstance().registerCmdForAM(AM_FROM, AM_TO,mAM);
                //注册调频指令fm
                RemoteSpeak.getInstance().registerCmdForFM(FM_FROM,FM_TO,mFM);
            }
        });
    }

    public void unbind(View view){
        Log.d(TAG, "unbind click  ");
        RemoteSpeak.getInstance().unRegisterGlobalCmd(MainActivity.this,mGlobalCmd);
        RemoteSpeak.getInstance().unRegisterCustomCmd(MainActivity.this,mCustomCmd);
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

    ICmdAM mAM = new ICmdAM() {
        @Override
        public void onCommand(String cmdKey, int am) throws RemoteException {
            Log.d(TAG,"调幅 AM 指令 cmdKey: "+cmdKey+", am: "+am);
        }
    };

    ICmdFM mFM = new ICmdFM() {
        @Override
        public void onCommand(String cmdKey, float fm) throws RemoteException {
            Log.d(TAG,"调频 FM 指令 cmdKey: "+cmdKey+", fm: "+fm);
        }
    };


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
}
