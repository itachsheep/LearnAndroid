package com.skyworthauto.speak.txz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.skyworthauto.sdk.define.McuCmdType;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.mcu.OnlineMusicManager;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZMusicManager.MusicModel;
import com.txznet.sdk.TXZStatusManager.StatusListener;

public class SkyStatusListener implements StatusListener {
    private static final String TAG = "SkyStatusListener";

    private static final String ACTION_PLAY_STATUS_CHANGE =
            "com.txznet.music.action.PLAY_STATUS_CHANGE";
    private static final String STATUS = "status"; // 1(缓冲),2(播放),3(暂停),4(退出)
    private static final int STATUS_PLAY = 2;
    private static final int STATUS_PAUSE = 3;
    private static final int STATUS_EXIT = 4;

    private boolean mIsMusicPlay;
    private String mMusicName;
    private boolean mIsPlayingForeground;
    private int mLastStatus = STATUS_EXIT;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.d(TAG, "onReceive intent=" + intent.getAction());
            String action = intent.getAction();
            if (ACTION_PLAY_STATUS_CHANGE.equals(action)) {
                int state = intent.getIntExtra(STATUS, STATUS_PAUSE);
                onTxzMusicStateChange(state);
            } else if (Constant.ACTION_ON_ACTIVITY_START.equals(action)) {
                onActivityChanged(intent);
            }
        }

    };

    public SkyStatusListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PLAY_STATUS_CHANGE);
        filter.addAction(Constant.ACTION_ON_ACTIVITY_START);
        SpeakApp.getApp().registerReceiver(mReceiver, filter);
    }

    private void onTxzMusicStateChange(int curState) {
        if (mLastStatus == curState) {
            return;
        }

        mLastStatus = curState;
        mIsMusicPlay = isMusicPlay(curState);
        if (mIsMusicPlay) {
            OnlineMusicManager.getManager().requestToOnLineMusic();
        }
        noticeSystemUIPlayStatus();
    }

    private String getMusicTitle() {
        try {
            MusicModel musicModel = TXZMusicManager.getInstance().getCurrentMusicModel();
            L.d(TAG, "onMusicPlay model=" + musicModel);
            return musicModel.getTitle();
        } catch (Exception e) {

        }
        return Constant.EMPTY;
    }

    protected void onActivityChanged(Intent intent) {
        boolean isPlayingForeground = isTxzMusicActivityOnTop(intent);
        if (mIsPlayingForeground == isPlayingForeground) {
            return;
        }

        mIsPlayingForeground = isPlayingForeground;
        noticeSystemUIPlayStatus();
    }

    private boolean isTxzMusicActivityOnTop(Intent intent) {
        String packageName = null;
        String activityComponent = intent.getStringExtra(Constant.ACTIVITY_INFO);

        try {
            String[] activtyInfo = activityComponent.split("/");
            packageName = activtyInfo[0];
        } catch (Exception e) {
        }

        return Constant.PACKAGE_NAME_TXZ_MUSIC.equals(packageName);
    }

    protected boolean isMusicPlay(int state) {
        L.d(TAG, "isMusicPlay status=" + state);
        return state == STATUS_PLAY;
    }

    public void noticeSystemUIPlayStatus() {
        L.d(TAG, "noticeSystemUIPlayStatus");
        if (McuServiceManager.getInstance().isInOnlineMusicSource()) {
            sendBroadcast();
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent(Constant.ACTION_SOURCE_CHANGE);
        intent.putExtra("source", McuCmdType.SOURCE_ONLINE_MUSIC);
        if (mIsMusicPlay) {
            mMusicName = getMusicTitle();
            intent.putExtra("status", mIsPlayingForeground ? 0 : 1);
        } else {
            intent.putExtra("status", 0);
        }

        intent.putExtra("back", !mIsPlayingForeground);
        intent.putExtra("name", null != mMusicName ? mMusicName : "");
        SpeakApp.getApp().sendBroadcast(intent);
    }

    @Override
    public void onMusicPlay() {
    }

    @Override
    public void onMusicPause() {
    }

    @Override
    public void onBeginCall() {
        // L.d(TAG, "电话开始");
    }

    @Override
    public void onEndCall() {
        // L.d(TAG, "电话结束");
    }

    @Override
    public void onBeginAsr() {
        L.d(TAG, "onBeginAsr");
    }

    @Override
    public void onBeepEnd() {
        L.d(TAG, "onBeepEnd");
    }

    @Override
    public void onEndAsr() {
        L.d(TAG, "onEndAsr");
    }

    @Override
    public void onBeginTts() {
        L.d(TAG, "onBeginTts");
        // AudioControl.getInstance().pauseMusicWhileTxzTtsStart();
        broadCastTtsBegin();
    }

    @Override
    public void onEndTts() {
        L.d(TAG, "onEndTts");
        // AudioControl.getInstance().resumeMusicWhileTxzTtsEnd();
    }

    private void broadCastTtsBegin() {
        Intent intent = new Intent(Constant.ACTION_TXZ_BEGIN_TTS);
        SpeakApp.getApp().sendBroadcast(intent);
    }

    public void exit() {
        SpeakApp.getApp().unregisterReceiver(mReceiver);
    }
}
