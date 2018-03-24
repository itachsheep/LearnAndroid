package com.skyworthauto.speak.cmd;

public abstract class MusicCommand implements ICommand {
    
    private IMusicExecutor mMusicExecutor;

    public MusicCommand(IMusicExecutor musicExecutor) {
        mMusicExecutor = musicExecutor;
    }
    
    public IMusicExecutor getMusicExecutor() {
        return mMusicExecutor;
    }
}
