package com.skyworthauto.speak.music;

import com.skyworthauto.speak.util.DebugUtil;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZMusicManager.MusicModel;
import com.txznet.sdk.TXZMusicManager.MusicTool;
import com.txznet.sdk.TXZMusicManager.MusicToolStatusListener;
import com.txznet.sdk.music.TXZMusicTool;

/**
 * Created by SDT14324 on 2017/11/21.
 */

public class TxzMusicTool implements MusicTool {
    private String tag = "TxzMusicTool";
    private TxzMusicToolStatusListener musicToolStatusListener = new TxzMusicToolStatusListener();

    @Override
    public void unfavourMusic() {
        LogUtils.i(tag,"unfavourMusic");
    }

    @Override
    public void switchSong() {
        LogUtils.i(tag,"switchSong");
    }

    @Override
    public void switchModeRandom() {
        LogUtils.i(tag,"switchModeRandom");
    }

    @Override
    public void switchModeLoopOne() {
        LogUtils.i(tag,"switchModeLoopOne");
    }

    @Override
    public void switchModeLoopAll() {
        LogUtils.i(tag,"switchModeLoopAll");
    }

    @Override
    public void setStatusListener(MusicToolStatusListener listener) {
        // TODO 状态监听器，如果实现自己的音乐工具，请将监听器记录下来，再对应状态变化时，使用该监听器来通知同行者
        LogUtils.i(tag,"setStatusListener listener: "+listener);
       // setStatusListener(musicToolStatusListener);
        //TXZStatusManager.getInstance().addStatusListener(statusListener);

    }

    @Override
    public void prev() {
        LogUtils.i(tag,"prev ");
        //TXZMusicManager.getInstance().prev();
    }

    @Override
    public void playRandom() {
        LogUtils.i(tag,"playRandom ");
    }

    @Override
    public void playMusic(MusicModel musicModel) {
        LogUtils.i(tag,"playMusic ");
        String title = musicModel.getTitle();
        String album = musicModel.getAlbum();
        String[] artist = musicModel.getArtist();
        String[] keywords = musicModel.getKeywords();
        TXZMusicTool.getInstance().playMusic(musicModel);
        LogUtils.i(tag,"playMusic 搜索标题是" + title + "专辑名称是" + album + "歌手是"
                + DebugUtil.convertArrayToString(artist) + "关键字是"
                + DebugUtil.convertArrayToString(keywords) + "的歌曲并播放");


    }

    @Override
    public void playFavourMusic() {
        LogUtils.i(tag,"playFavourMusic ..");
    }

    @Override
    public void play() {
        LogUtils.i(tag,"play ..");
        //TXZMusicManager.getInstance().play();
    }

    @Override
    public void continuePlay() {
        LogUtils.i(tag,"continuePlay ");
    }


    @Override
    public void pause() {
        LogUtils.i(tag,"pause ..");
     // TXZMusicManager.getInstance().pause();
    }

    @Override
    public void next() {
        LogUtils.i(tag," next ..");
      //  TXZMusicManager.getInstance().next();
    }

    @Override
    public boolean isPlaying() {
        // TODO 返回真实的播放器状态
        boolean playing = TXZMusicManager.getInstance().isPlaying();
        LogUtils.i(tag," isPlaying playing; "+playing);
        return playing;
    }

    @Override
    public MusicModel getCurrentMusicModel() {
        // TODO 返回真实的播放的歌曲信息
        LogUtils.i(tag," getCurrentMusicModel : ..");
        return null;
    }

    @Override
    public void favourMusic() {
        LogUtils.i(tag,"favourMusic ..");
    }

    @Override
    public void exit() {
        LogUtils.i(tag," exit ..");
    }
}
