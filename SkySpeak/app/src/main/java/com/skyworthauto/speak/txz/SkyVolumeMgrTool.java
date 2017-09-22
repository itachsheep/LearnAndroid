package com.skyworthauto.speak.txz;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZSysManager.VolumeMgrTool;
import com.txznet.sdk.TXZTtsManager;

public class SkyVolumeMgrTool implements VolumeMgrTool {
    private static final String TAG = "SkyVolumeMgrTool";

    private String mVoiceTip = "";

    public SkyVolumeMgrTool() {
        mVoiceTip = SpeakApp.getApp().getString(R.string.not_support_voice_control);
    }

    @Override
    public void mute(boolean enable) {
        if (enable) {
            L.d(TAG, "close voice");
        } else {
            L.d(TAG, "open voice");
        }
        TXZTtsManager.getInstance().speakText(mVoiceTip);
    }

    @Override
    public boolean isMaxVolume() {
        return false;
    }

    @Override
    public boolean isMinVolume() {
        return false;
    }

    @Override
    public void minVolume() {
        L.d(TAG, "minVolume");
        TXZTtsManager.getInstance().speakText(mVoiceTip);
    }

    @Override
    public void maxVolume() {
        L.d(TAG, "maxVolume");
        TXZTtsManager.getInstance().speakText(mVoiceTip);
    }

    @Override
    public void incVolume() {
        L.d(TAG, "incVolume");
        TXZTtsManager.getInstance().speakText(mVoiceTip);
    }

    @Override
    public void decVolume() {
        L.d(TAG, "decVolume");
        TXZTtsManager.getInstance().speakText(mVoiceTip);
    }
}
