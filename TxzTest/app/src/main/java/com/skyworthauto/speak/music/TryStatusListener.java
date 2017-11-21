package com.skyworthauto.speak.music;

import com.skyworthauto.speak.util.DebugUtil;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZStatusManager.StatusListener;
import com.txznet.sdk.TXZMusicManager.MusicModel;

/**
 * Created by SDT14324 on 2017/11/21.
 */

public class TryStatusListener implements StatusListener {
    private String tag = "TryStatusListener";
    @Override
    public void onMusicPlay() {
        MusicModel model = TXZMusicManager.getInstance().getCurrentMusicModel();
        if (model != null) {
            LogUtils.i(tag,"TryStatusListener onMusicPlay：" + model.getTitle() + "-"
                    + DebugUtil.convertArrayToString(model.getArtist()));
        } else {
            LogUtils.i(tag,"TryStatusListener onMusicPlay");
        }
    }

    @Override
    public void onMusicPause() {
        LogUtils.i(tag,"TryStatusListener onMusicPause");
    }

    @Override
    public void onEndTts() {
        LogUtils.i(tag,"TryStatusListener onEndTts");
    }

    @Override
    public void onEndCall() {
        LogUtils.i(tag,"TryStatusListener onEndCall");
    }

    @Override
    public void onEndAsr() {
        LogUtils.i(tag,"TryStatusListener onEndAsr");
    }

    @Override
    public void onBeginTts() {
        LogUtils.i(tag,"TryStatusListener onEndTts");
    }

    @Override
    public void onBeginCall() {
        LogUtils.i(tag,"TryStatusListener onBeginCall");
    }

    @Override
    public void onBeginAsr() {
        LogUtils.i(tag,"TryStatusListener onEndTts");
    }

    @Override
    public void onBeepEnd() {
        LogUtils.i(tag,"TryStatusListener onBeepEnd");
    }
}
