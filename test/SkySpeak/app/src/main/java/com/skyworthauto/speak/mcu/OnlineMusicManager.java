package com.skyworthauto.speak.mcu;

import com.skyworthauto.sdk.define.McuCmdType;
import com.skyworthauto.sdk.manager.mcu.SkyMusicManager;
import com.skyworthauto.sdk.manager.mcu.SkySourceManager;
import com.skyworthauto.speak.txz.AudioControl;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZMusicManager;

public class OnlineMusicManager extends SkySourceManager {
    private static final String TAG = OnlineMusicManager.class.getSimpleName();

    private static boolean sFirstRequest = true;

    private static SkySourceEventListener mSourceListener = new SkySourceEventListener() {

        private boolean mIsPlayingWhenInterrup = false;

        private boolean isTxzMusicPlaying() {
            try {
                return TXZMusicManager.getInstance().isPlaying();
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public void onSourceIn() {
            L.d(TAG, "onSourceIn...");
            // synchronized (OnlineMusicManager.class) {
            // if (!isTxzMusicPlaying()) {
            // TXZMusicManager.getInstance().play();
            // }
            // }
        }

        @Override
        public void onSourceOut() {
            L.d(TAG, "onSourceOut...");
            synchronized (OnlineMusicManager.class) {
                if (isTxzMusicPlaying()) {
                    AudioControl.getInstance().pauseTXZMusic();
                }
            }
        }

        @Override
        public void onPrevouseTrack() {
            L.d(TAG, "onPrevouseTrack...");
            synchronized (OnlineMusicManager.class) {
                TXZMusicManager.getInstance().prev();
            }

        }

        @Override
        public void onNextTrack() {
            L.d(TAG, "onNextTrack...");
            synchronized (OnlineMusicManager.class) {
                TXZMusicManager.getInstance().next();
            }

        }

        @Override
        public void onPlayOrPuase() {
            L.d(TAG, "onPlayOrPause...");
            if (isTxzMusicPlaying()) {
                AudioControl.getInstance().pauseTXZMusic();
            } else {
                TXZMusicManager.getInstance().play();
            }

        }

        @Override
        public void onExtEventOut(int i) {
            L.d(TAG, "onExtEventOut...");
            synchronized (OnlineMusicManager.class) {
                if (mIsPlayingWhenInterrup) {
                    AudioControl.getInstance().resumeTXZMusic();
                    mIsPlayingWhenInterrup = false;
                }
            }
        }

        @Override
        public void onExtEventIn(int i) {
            L.d(TAG, "onExtEventIn...");
            synchronized (OnlineMusicManager.class) {
                mIsPlayingWhenInterrup = true;
                AudioControl.getInstance().pauseTXZMusicTransient();
            }
        }

        @Override
        public void onAccOff() {
            L.d(TAG, "onAccOff...");
        }

    };

    private static OnlineMusicManager mManager;

    private OnlineMusicManager() {
        setSkySourceEventListener(mSourceListener);
    }

    public static OnlineMusicManager getManager() {
        synchronized (SkyMusicManager.class) {
            if (null == mManager) {
                mManager = new OnlineMusicManager();
            }

            return mManager;
        }
    }


    //    public void init() {
    //        sSkyOnlineMusicManager = SkyOnlineMusicManager.getInstance(SpeakApp.getApp());
    //        sSkyOnlineMusicManager.setSourceListener(mSourceListener);
    //    }
    //
    //    public static void onResume() {
    //        sSkyOnlineMusicManager.onResume();
    //    }

    public void requestToOnLineMusic() {
        if (!McuServiceManager.getInstance().isInOnlineMusicSource() || sFirstRequest) {
            L.d(TAG, "requestToOnLineMusic...");
            sFirstRequest = false;
            requestToMcuSource(true, McuCmdType.SOURCE_ONLINE_MUSIC);
        }
    }

    public void requestToMp3() {
        L.d(TAG, "requestToMp3...");
        requestToMcuSource(false, McuCmdType.SOURCE_MP3);
    }

    public void requestToMp4() {
        L.d(TAG, "requestToMp4...");
        requestToMcuSource(false, McuCmdType.SOURCE_MP4);
    }

    @Override
    public int getSelfManagerSource() {
        return McuCmdType.SOURCE_ONLINE_MUSIC;
    }

    @Override
    protected int getSelfModeFid() {
        return 0;
    }
}
