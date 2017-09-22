package com.skyworthauto.speak.util;

import android.content.Context;
import android.content.Intent;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.txz.AudioControl;
import com.txznet.sdk.TXZMusicManager;

public class MusicControlUtil {
    private static final String TAG = MusicControlUtil.class.getSimpleName();

    public static void controlOnlineMusic(Context context, String cmdId) {
        L.d(TAG, "controlOnlineMusic cmd=" + cmdId);
        if (context.getString(R.string.media_repeat_sequence_id).equals(cmdId)) {
            TXZMusicManager.getInstance().switchModeLoopAll();
        } else if (context.getString(R.string.media_repeat_single_id).equals(cmdId)) {
            TXZMusicManager.getInstance().switchModeLoopOne();
        } else if (context.getString(R.string.media_repeat_random_id).equals(cmdId)) {
            TXZMusicManager.getInstance().switchModeRandom();
        } else if (context.getString(R.string.media_play_id).equals(cmdId)) {
            TXZMusicManager.getInstance().play();
        } else if (context.getString(R.string.media_pause_id).equals(cmdId)) {
            // TXZMusicManager.getInstance().pause();
            AudioControl.getInstance().pauseTXZMusic();
        } else if (context.getString(R.string.media_track_prev_id).equals(cmdId)) {
            TXZMusicManager.getInstance().prev();
        } else if (context.getString(R.string.media_track_next_id).equals(cmdId)) {
            TXZMusicManager.getInstance().next();
        }
    }

    public static void controlLocalMusic(Context context, int type, int value) {
        L.d(TAG, "controlLocalMusic type=" + type + ", value=" + value);
        Intent intent = new Intent(Constant.ACTION_VOICE_CTRL_PLAYBACK);
        intent.putExtra(Constant.EXTRA_MEDIA_PLAYBACK_TYPE, type);
        intent.putExtra(getTypeName(type), value);

        context.sendBroadcast(intent);
    }

    public static String getTypeName(int playBackType) {
        switch (playBackType) {
            case Constant.PLAYBACK_TYPE_REPEAT:
                return Constant.EXTRA_PLAYBACK_REPEAT;
            case Constant.PLAYBACK_TYPE_PLAY:
                return Constant.EXTRA_PLAYBACK_PLAY;
            case Constant.PLAYBACK_TYPE_TRACK:
                return Constant.EXTRA_PLAYBACK_TRACK;
            default:
                return "";
        }
    }

}
