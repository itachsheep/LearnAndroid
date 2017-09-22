package com.skyworthauto.speak.cmd;

import android.content.Context;

import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.util.MusicControlUtil;
import com.skyworthauto.speak.util.L;

public class ControlMusic extends CmdSpeakable {
    private static final String TAG = ControlMusic.class.getSimpleName();

    private int mPlayBackType;
    private int mPlayBackValue;

    public ControlMusic(Context context, int cmdArrId, int cmdId, int speakId, int playBackType,
            int playBackValue) {
        super(context, cmdArrId, cmdId, speakId);
        mPlayBackType = playBackType;
        mPlayBackValue = playBackValue;
    }

    @Override
    public void run() {
        controlMusic();
    }

    private void controlMusic() {
        try {
            if (McuServiceManager.getInstance().isInLocalMusicSource()) {
                MusicControlUtil.controlLocalMusic(mContext, mPlayBackType, mPlayBackValue);
            } else if (McuServiceManager.getInstance().isInOnlineMusicSource()) {
                MusicControlUtil.controlOnlineMusic(mContext, mCmdKey);
            }
        } catch (Exception e) {
            L.e(TAG, "sendBroadcast error:" + e.getMessage());
        }
    }

}
