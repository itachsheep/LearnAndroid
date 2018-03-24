package com.skyworthauto.speak.cmd;

import android.content.Context;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.txz.AudioControl;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.MusicControlUtil;
import com.txznet.sdk.TXZMusicManager;

import java.util.Collection;

public class MusicCmdProvider implements ICmdProvider {

    private static final String TAG = "MusicCmdProvider";

    @Override
    public int getId() {
        return CmdProviderFactory.ID_CONTROL_MUSIC;
    }

    @Override
    public Collection<? extends CmdSpeakable> getList() {
        return CmdProviderFactory.createMusicCmds(SpeakApp.getApp());
    }

    @Override
    public ICommand getCommand(CmdSpeakable cmdData) {
        Context context = SpeakApp.getApp();
        IMusicExecutor musicExecutor = getMusicExecutor();
        L.d(TAG, "musicExecutor=" + musicExecutor);
        if (musicExecutor == null) {
            return null;
        }

        if (context.getString(R.string.media_repeat_sequence_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().repeatSequence();
                }

            };
        } else if (context.getString(R.string.media_repeat_single_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().repeatSingle();
                }

            };
        } else if (context.getString(R.string.media_repeat_random_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().repeatRandom();
                }

            };
        } else if (context.getString(R.string.media_play_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().play();
                }

            };
        } else if (context.getString(R.string.media_pause_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().pause();
                }

            };
        } else if (context.getString(R.string.media_track_prev_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().prev();
                }

            };
        } else if (context.getString(R.string.media_track_next_id).equals(cmdData.mCmdKey)) {
            return new MusicCommand(musicExecutor) {

                @Override
                public void execute() {
                    getMusicExecutor().next();
                }

            };
        }
        return null;
    }

    public static IMusicExecutor getMusicExecutor() {
        L.d(TAG, "getCurrentSource=" + McuServiceManager.getInstance().getCurrentSource());

        if (McuServiceManager.getInstance().isInLocalMusicSource()) {
            return new SkyMusicExecutor();
        } else if (McuServiceManager.getInstance().isInOnlineMusicSource()) {
            return new TxzMusicExecutor();
        } else if (McuServiceManager.getInstance().isInVideoSource()) {
            return new VideoExecutor();
        } else if (McuServiceManager.getInstance().isInBtMusicSource()) {
            return new BtMusicExecutor();
        }
        return null;
    }

}
