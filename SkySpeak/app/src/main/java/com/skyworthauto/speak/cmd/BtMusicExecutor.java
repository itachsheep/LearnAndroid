package com.skyworthauto.speak.cmd;

import com.skyworthauto.speak.util.ISpeakControl;

public class BtMusicExecutor implements IMusicExecutor {
    @Override
    public void play() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.PLAY_MUSIC);
    }

    @Override
    public void pause() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.PAUSE_MUSIC);
    }

    @Override
    public void prev() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.PREV_MUSIC);
    }

    @Override
    public void next() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.NEXT_MUSIC);
    }

    @Override
    public void repeatSequence() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.SEQUENCE_MUSIC);
    }

    @Override
    public void repeatSingle() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.SINGLE_MUSIC);
    }

    @Override
    public void repeatRandom() {
        ISpeakControl.sentBroadCast(ISpeakControl.ACTION_CONTROL_BT_MUSIC, ISpeakControl.CTRL_MUSIC,
                ISpeakControl.RANDOM_MUSIC);
    }
}
