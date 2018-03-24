package com.skyworthauto.speak.music;

import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZMusicManager;

/**
 * Created by SDT14324 on 2017/11/23.
 */

public class TxzMusicToolStatusListener implements TXZMusicManager.MusicToolStatusListener {
    private String tag = TxzMusicToolStatusListener.class.getSimpleName();
    @Override
    public void onStatusChange() {
        LogUtils.i(tag,"onStatusChange");
    }

    @Override
    public void onStatusChange(int state) {
        LogUtils.i(tag,"onStatusChange state: "+state);
    }

    @Override
    public void playMusic(TXZMusicManager.MusicModel musicModel) {
        LogUtils.i(tag,"playMusic musicModel: "+musicModel);
    }

    @Override
    public void endMusic(TXZMusicManager.MusicModel musicModel) {
        LogUtils.i(tag,"endMusic musicModel: "+musicModel);
    }

    @Override
    public void T(int i, int i1) {
        LogUtils.i(tag,"T i: "+i+", i1: "+i1);
    }
}
