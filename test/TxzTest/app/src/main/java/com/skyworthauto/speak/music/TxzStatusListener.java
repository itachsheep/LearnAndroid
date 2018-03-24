package com.skyworthauto.speak.music;

import com.skyworthauto.speak.util.DebugUtil;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZStatusManager.StatusListener;
import com.txznet.sdk.TXZMusicManager.MusicModel;

/**
 * Created by SDT14324 on 2017/11/21.
 */

public class TxzStatusListener implements StatusListener {
    private String tag = "TxzStatusListener";
    @Override
    public void onMusicPlay() {
        MusicModel model = TXZMusicManager.getInstance().getCurrentMusicModel();
        if (model != null) {
            LogUtils.i(tag,"TxzStatusListener onMusicPlay：" + model.getTitle() + "-"
                    + DebugUtil.convertArrayToString(model.getArtist()));
        } else {
            LogUtils.i(tag,"TxzStatusListener onMusicPlay");
        }
    }

    @Override
    public void onMusicPause() {
        LogUtils.i(tag," onMusicPause");
    }

    @Override
    public void onEndTts() {
        LogUtils.i(tag," onEndTts");
    }

    @Override
    public void onEndCall() {
        LogUtils.i(tag," onEndCall");
    }

    @Override
    public void onEndAsr() {
        LogUtils.i(tag," onEndAsr");
    }

    @Override
    public void onBeginTts() {
        LogUtils.i(tag," onEndTts");
    }

    @Override
    public void onBeginCall() {
        LogUtils.i(tag," onBeginCall");
    }

    @Override
    public void onBeginAsr() {
        LogUtils.i(tag," onEndTts");
    }

    @Override
    public void onBeepEnd() {
        LogUtils.i(tag," onBeepEnd");
    }
}
