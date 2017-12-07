package com.skyworthauto.test;

import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZMusicManager;

/**
 * Created by SDT14324 on 2017/11/23.
 */

public class TxzMusicToolStatusListener implements TXZMusicManager.MusicToolStatusListener {
    private String tag = TxzMusicToolStatusListener.class.getSimpleName();
    @Override
    public void onStatusChange() {
        L.i(tag,"onStatusChange");
    }

    @Override
    public void onStatusChange(int state) {
        L.i(tag,"onStatusChange state: "+state);
    }

    @Override
    public void playMusic(TXZMusicManager.MusicModel musicModel) {
        L.i(tag,"playMusic musicModel: "+musicModel);
    }

    @Override
    public void endMusic(TXZMusicManager.MusicModel musicModel) {
        L.i(tag,"endMusic musicModel: "+musicModel);
    }

    @Override
    public void T(int i, int i1) {
        L.i(tag,"T i: "+i+", i1: "+i1);
    }
}
