package com.skyworthauto.speak.txz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.cmd.MusicCmdProvider;
import com.skyworthauto.speak.cmd.OpenActivity;
import com.skyworthauto.speak.cmd.OtherCmds.OpenWifi;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.Utils;
import com.txznet.sdk.TXZResourceManager;
import com.txznet.sdk.TXZSenceManager.SenceTool;
import com.txznet.sdk.TXZSenceManager.SenceType;

import org.json.JSONException;
import org.json.JSONObject;

public class SkySenceTool implements SenceTool {
    private static final String TAG = SkySenceTool.class.getSimpleName();

    private static final String SELECTOR = "selector";
    private static final String SENCE = "sence";
    private static final String SCENE = "scene";
    private static final String ITEM_INDEX = "ITEM_INDEX";
    private static final String COMM_SELECT = "commSelect";
    private static final String MUSIC = "music";
    private static final String AUDIO = "audio";
    private static final String TYPE = "type";
    private static final String PLAY = "play";
    private static final String PLAY_RANDOM = "playRandom";
    private static final String MODEL = "model";
    private static final String ACTION = "action";
    private static final String NEXT = "next";
    private static final String PREV = "prev";
    private static final String LIGHT_DOWN = "light_down";
    private static final String LIGHT_UP = "light_up";
    private static final String VOL_ON = "vol_on";
    private static final String VOL_OFF = "vol_off";
    private static final String VOL_MIN = "vol_min";
    private static final String VOL_MAX = "vol_max";
    private static final String VOL_UP = "vol_up";
    private static final String VOL_DOWN = "vol_down";
    private static final String WIFI_ON = "wifi_on";
    private static final String CMD = "cmd";

    private String mCurScene = Constant.EMPTY;
    private String mLastScene = Constant.EMPTY;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "onReceive action=" + action);
            if (Constant.ACTION_RECORD_VIEW_HIDED.equals(action)
                    || Constant.ACTION_RECORD_VIEW_SHOWING.equals(action)) {

                mCurScene = Constant.EMPTY;
                mLastScene = Constant.EMPTY;
            }
        }

    };

    public SkySenceTool() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_RECORD_VIEW_HIDED);
        filter.addAction(Constant.ACTION_RECORD_VIEW_SHOWING);
        SpeakApp.getApp().registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public boolean process(SenceType type, String data) {
        L.d(TAG, "process,type=" + type + ",data=" + data);

        JSONObject json;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        if (!mLastScene.equals(mCurScene)) {
            mLastScene = mCurScene;
        }

        mCurScene = getScene(json);

        if (TextUtils.isEmpty(mCurScene)) {
            return processCmd(json);
        }

        if (MUSIC.equals(mCurScene)) {
            return processMusicScene(json);
        } else if (AUDIO.equals(mCurScene)) {
            return processAudioScene(json);
        } else if (SELECTOR.equals(mCurScene)) {
            return processSelectorScene(json);
        }
        return false;
    }

    private String getScene(JSONObject json) {
        String sence = json.optString(SENCE);
        if (!TextUtils.isEmpty(sence)) {
            return sence;
        }

        return json.optString(SCENE);
    }

    private boolean processCmd(JSONObject json) {
        String cmd = json.optString(CMD);
        if (isVolumeControl(cmd)) {
            TXZResourceManager.getInstance()
                    .speakTextOnRecordWin(Utils.getString(R.string.please_manual_control_volume),
                            true, null);
            return true;
        } else if (isOpenWifi(cmd)) {
            OpenActivity openWifi = new OpenWifi(SpeakApp.getApp());
            TXZResourceManager.getInstance()
                    .speakTextOnRecordWin(openWifi.mSpeakStr, true, openWifi);
            return true;
        } else if (isLightControl(cmd)) {
            TXZResourceManager.getInstance()
                    .speakTextOnRecordWin(Utils.getString(R.string.light_control_unenabled), true,
                            null);
            return true;
        }
        return false;
    }

    private boolean isOpenWifi(String cmd) {
        //        return WIFI_ON.equals(cmd);
        return false;
    }

    private boolean isVolumeControl(String cmd) {
        /*return VOL_DOWN.equals(cmd) || VOL_UP.equals(cmd) || VOL_MAX.equals(cmd)
                || VOL_MIN.equals(cmd)
                || VOL_OFF.equals(cmd) || VOL_ON.equals(cmd);*/
        return false;
    }

    private boolean isLightControl(String cmd) {
        //        return LIGHT_UP.equals(cmd) || LIGHT_DOWN.equals(cmd);
        return false;
    }

    private boolean processMusicScene(JSONObject json) {
        String action = json.optString(ACTION);

        if (PREV.equals(action)) {
            prevMusic();
            return true;
        }

        if (NEXT.equals(action)) {
            nextMusic();
            return true;
        }

        if (PLAY.equals(action) || PLAY_RANDOM.equals(action)) {
            pauseLocalVoice();
            return false;
        }

        return false;
    }

	 private void prevMusic() {
        TXZResourceManager.getInstance()
                .speakTextOnRecordWin(Utils.getString(R.string.media_track_prev_speak), true,
                        new Runnable() {

                            @Override
                            public void run() {
                                MusicCmdProvider.getMusicExecutor().prev();
                            }
                        });
    }

    private void nextMusic() {
        TXZResourceManager.getInstance()
                .speakTextOnRecordWin(Utils.getString(R.string.media_track_next_speak), true,
                        new Runnable() {

                            @Override
                            public void run() {
                                MusicCmdProvider.getMusicExecutor().next();
                            }
                        });
    }

    private void pauseLocalVoice() {
        AudioControl.getInstance().setNeedPauseLocalVoice(true);
    }

    private boolean processAudioScene(JSONObject json) {
        String action = json.optString(ACTION);
        if (PLAY.equals(action) || PLAY_RANDOM.equals(action)) {
            pauseLocalVoice();
        }
        return false;
    }

    private boolean processSelectorScene(JSONObject json) {
        L.d(TAG, "processSelectorScene" + ",mLastScene=" + mLastScene);
        String action = json.optString(ACTION);
        String type = json.optString(TYPE);
        if ((AUDIO.equals(mLastScene) || MUSIC.equals(mLastScene)) && COMM_SELECT.equals(action)
                && type.startsWith(ITEM_INDEX)) {
            pauseLocalVoice();
        }
        return false;
    }

    public void exit() {
        SpeakApp.getApp().unregisterReceiver(mBroadcastReceiver);
    }

}
