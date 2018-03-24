package com.skyworthauto.speak.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.music.TxzMusicTool;
import com.skyworthauto.speak.music.TxzMusicToolStatusListener;
import com.skyworthauto.speak.music.TxzStatusListener;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZMusicManager.MusicToolType;
import com.txznet.sdk.TXZStatusManager;

/**
 * Created by SDT14324 on 2017/11/21.
 */

public class MusicActivity extends AppCompatActivity {
    private String tag = "MusicActivity";
    private TxzMusicTool musicTool;
    private TxzStatusListener musicStatusListener;
    private TxzMusicToolStatusListener musicToolStatusListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        LogUtils.i(tag,"oncreate ");
        musicTool = new TxzMusicTool();
        musicStatusListener = new TxzStatusListener();
        musicToolStatusListener = new TxzMusicToolStatusListener();
        initFirstColumn();
        initSecondColumn();
        initThirdColumn();




    }

    private void initThirdColumn(){
        findViewById(R.id.bt_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(tag,"play..");
                TXZMusicManager.getInstance().play();
            }
        });

        findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXZMusicManager.getInstance().pause();
            }
        });

        findViewById(R.id.bt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXZMusicManager.getInstance().next();
            }
        });

        findViewById(R.id.bt_pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXZMusicManager.getInstance().prev();
            }
        });




    }

    private void initSecondColumn(){
        findViewById(R.id.bt_txz_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXZMusicManager.getInstance().setMusicTool(
                        MusicToolType.MUSIC_TOOL_TXZ);
            }
        });
    }

    private void initFirstColumn() {
        findViewById(R.id.bt_music_tool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(tag,"setMusicTool..");
                LogUtils.i(tag,"setMusicTool musicToolStatusListener: "+musicToolStatusListener);
                synchronized (MusicActivity.this){
                    musicTool.setStatusListener(null);
                    //musicTool.setStatusListener(musicToolStatusListener);
                    TXZMusicManager.getInstance().setMusicTool(musicTool);
                    TXZMusicManager.getInstance().setMusicTool(MusicToolType.MUSIC_TOOL_TXZ);
                }


            }
        });

        findViewById(R.id.bt_cancel_tool).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TXZMusicManager.getInstance().setMusicTool((TxzMusicTool) null);
            }
        });

        findViewById(R.id.bt_music_listener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(tag,"addStatusListener..");
                TXZStatusManager.getInstance().addStatusListener(musicStatusListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(tag,"onStop..");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(tag,"onDestroy..");
    }
}
