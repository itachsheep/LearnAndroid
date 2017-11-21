package com.skyworthauto.speak.music;

import com.skyworthauto.speak.util.DebugUtil;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZMusicManager.MusicModel;
import com.txznet.sdk.TXZMusicManager.MusicTool;
import com.txznet.sdk.TXZMusicManager.MusicToolStatusListener;
/**
 * Created by SDT14324 on 2017/11/21.
 */

public class TryMusicTool implements MusicTool {
    private String tag = "TryMusicTool";
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
        LogUtils.i(tag,"setStatusListener ");
    }

    @Override
    public void prev() {
        LogUtils.i(tag,"prev ");
    }

    @Override
    public void playRandom() {
        LogUtils.i(tag,"playRandom ");
    }

    @Override
    public void playMusic(MusicModel musicModel) {
        String title = musicModel.getTitle();
        String album = musicModel.getAlbum();
        String[] artist = musicModel.getArtist();
        String[] keywords = musicModel.getKeywords();
//        TXZMusicTool.getInstance().playMusic(musicModel);
        LogUtils.i(tag,"playMusic 搜索标题是" + title + "专辑名称是" + album + "歌手是"
                + DebugUtil.convertArrayToString(artist) + "关键字是"
                + DebugUtil.convertArrayToString(keywords) + "的歌曲并播放");
    }

    @Override
    public void playFavourMusic() {
        LogUtils.i(tag,"playFavourMusic 播放收藏歌曲");
    }

    @Override
    public void play() {
        LogUtils.i(tag,"play 开始播放歌曲");
    }

//    @Override
//    public void continuePlay() {
//
//    }

    @Override
    public void pause() {
        LogUtils.i(tag,"pause 暂停播放歌曲");
    }

    @Override
    public void next() {
        LogUtils.i(tag," next 播放下一首");
    }

    @Override
    public boolean isPlaying() {
        // TODO 返回真实的播放器状态

        LogUtils.i(tag," isPlaying : 返回真实的播放器状态");
        return false;
    }

    @Override
    public MusicModel getCurrentMusicModel() {
        // TODO 返回真实的播放的歌曲信息
        LogUtils.i(tag," getCurrentMusicModel : 返回真实的播放的歌曲信息");
        return null;
    }

    @Override
    public void favourMusic() {
        LogUtils.i(tag,"favourMusic 收藏当前音乐");
    }

    @Override
    public void exit() {
        LogUtils.i(tag," exit 退出音乐");
    }
}
