package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.util.AttributeSet;

import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.util.MusicControlUtil;
import com.skyworthauto.speak.util.L;

public class MusicIntentCmd extends IntentCmd {

    private static final String TAG = "MusicIntentCmd";

    public MusicIntentCmd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public MusicIntentCmd(Context context, int cmdArrId, int cmdId, int speakId, int action,
            int subAction, int value) {
        super(context, cmdArrId, cmdId, speakId, action, subAction, value);
    }
    
    @Override
    public void run() {
        try {
            if (McuServiceManager.getInstance().isInLocalMusicSource()) {
                super.run();
            } else if (McuServiceManager.getInstance().isInOnlineMusicSource()) {
                MusicControlUtil.controlOnlineMusic(mContext, mCmdKey);
            }
        } catch (Exception e) {
            L.e(TAG, "sendBroadcast error:" + e.getMessage());
        }
    }

}
