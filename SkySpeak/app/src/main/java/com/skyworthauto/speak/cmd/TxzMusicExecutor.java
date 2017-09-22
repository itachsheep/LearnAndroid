package com.skyworthauto.speak.cmd;

import com.skyworthauto.speak.txz.AudioControl;
import com.txznet.sdk.TXZMusicManager;

public class TxzMusicExecutor implements IMusicExecutor{

    @Override
    public void play() {
        TXZMusicManager.getInstance().play();
    }

    @Override
    public void pause() {
        // TXZMusicManager.getInstance().pause();
        AudioControl.getInstance().pauseTXZMusic();
    }

    @Override
    public void prev() {
        TXZMusicManager.getInstance().prev();
    }

    @Override
    public void next() {
        TXZMusicManager.getInstance().next();
    }

    @Override
    public void repeatSequence() {
        TXZMusicManager.getInstance().switchModeLoopAll();
    }

    @Override
    public void repeatSingle() {
        TXZMusicManager.getInstance().switchModeLoopOne();
    }

    @Override
    public void repeatRandom() {
        TXZMusicManager.getInstance().switchModeRandom();
    }

}
