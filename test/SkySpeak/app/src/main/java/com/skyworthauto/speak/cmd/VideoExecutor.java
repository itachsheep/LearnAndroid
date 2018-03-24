package com.skyworthauto.speak.cmd;


import com.skyworthauto.speak.util.ISpeakControl;

public class VideoExecutor implements IMusicExecutor {
    @Override
    public void play() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.PLAY_VIDEO);
    }

    @Override
    public void pause() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.PAUSE_VIDEO);
    }

    @Override
    public void prev() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.PREV_VIDEO);
    }

    @Override
    public void next() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.NEXT_VIDEO);
    }

    @Override
    public void repeatSequence() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.SEQUENCE_VIDEO);
    }

    @Override
    public void repeatSingle() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.SINGLE_VIDEO);
    }

    @Override
    public void repeatRandom() {
        ISpeakControl
                .sentBroadCast(ISpeakControl.ACTION_CONTROL_SKY_VIDEO, ISpeakControl.CTRL_VIDEO,
                        ISpeakControl.RANDOM_VIDEO);
    }
}
