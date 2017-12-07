package com.skyworthauto.test;


import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZMusicManager.MusicModel;
import com.txznet.sdk.TXZMusicManager.MusicTool;
import com.txznet.sdk.TXZMusicManager.MusicToolStatusListener;
import com.txznet.sdk.music.TXZMusicTool;

/**
 * Created by SDT14324 on 2017/11/21.
 */

public class TxzMusicTool implements MusicTool {
    private String tag = "TxzMusicTool##";
    private TxzMusicToolStatusListener musicToolStatusListener = new TxzMusicToolStatusListener();

    @Override
    public void unfavourMusic() {
        L.d(tag,"unfavourMusic");
    }

    @Override
    public void switchSong() {
        L.d(tag,"switchSong");
    }

    @Override
    public void switchModeRandom() {
        L.i(tag,"switchModeRandom");
    }

    @Override
    public void switchModeLoopOne() {
        L.i(tag,"switchModeLoopOne");
    }

    @Override
    public void switchModeLoopAll() {
        L.i(tag,"switchModeLoopAll");
    }

    @Override
    public void setStatusListener(MusicToolStatusListener listener) {
        // TODO 状态监听器，如果实现自己的音乐工具，请将监听器记录下来，再对应状态变化时，使用该监听器来通知同行者
        L.i(tag,"setStatusListener listener: "+listener);
       // setStatusListener(musicToolStatusListener);
        //TXZStatusManager.getInstance().addStatusListener(statusListener);

    }

    @Override
    public void prev() {
        L.i(tag,"prev ");
        //TXZMusicManager.getInstance().prev();
    }

    @Override
    public void playRandom() {
        L.i(tag,"playRandom ");
    }

    @Override
    public void playMusic(MusicModel musicModel) {
        L.i(tag,"playMusic ");
        String title = musicModel.getTitle();
        String album = musicModel.getAlbum();
        String[] artist = musicModel.getArtist();
        String[] keywords = musicModel.getKeywords();
        //TXZMusicTool.getInstance().playMusic(musicModel);
        L.i(tag,"playMusic 搜索标题是" + title + "专辑名称是" + album + "歌手是");


    }

    @Override
    public void playFavourMusic() {
        L.i(tag,"playFavourMusic ..");
    }

    @Override
    public void play() {
        L.i(tag,"play ..");
        //TXZMusicManager.getInstance().play();
    }

    @Override
    public void continuePlay() {
        L.i(tag,"continuePlay ");
    }


    @Override
    public void pause() {
        L.i(tag,"pause ..");
     // TXZMusicManager.getInstance().pause();
    }

    @Override
    public void next() {
        L.i(tag," next ..");
      //  TXZMusicManager.getInstance().next();
    }

    @Override
    public boolean isPlaying() {
        // TODO 返回真实的播放器状态
        boolean playing = TXZMusicManager.getInstance().isPlaying();
        L.i(tag," isPlaying playing; "+playing);
        return playing;
    }

    @Override
    public MusicModel getCurrentMusicModel() {
        // TODO 返回真实的播放的歌曲信息
        L.i(tag," getCurrentMusicModel : ..");
        return null;
    }

    @Override
    public void favourMusic() {
        L.i(tag,"favourMusic ..");
    }

    @Override
    public void exit() {
        L.i(tag," exit ..");
    }
}
