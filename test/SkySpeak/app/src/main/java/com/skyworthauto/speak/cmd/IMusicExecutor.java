package com.skyworthauto.speak.cmd;

public interface IMusicExecutor {
    void play();

    void pause();

    void prev();

    void next();

    void repeatSequence();

    void repeatSingle();

    void repeatRandom();
}
