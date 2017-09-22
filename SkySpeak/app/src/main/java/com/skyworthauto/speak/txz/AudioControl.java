package com.skyworthauto.speak.txz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Handler;
import android.os.Message;

import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.mcu.OnlineMusicManager;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;

public class AudioControl {
    private static final String TAG = AudioControl.class.getSimpleName();

    private AudioManager mAudioManager;

    private int mNaviPauseMusicCount;

    private static AudioControl sInstance;
    private boolean mNeedPauseLocalVoice = false;

    private boolean mHasPauseMusicWithTxz = false;

    private OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    L.d(TAG, "AudioFocus: AUDIOFOCUS_LOSS");
                    if (needPauseLocalVoice()) {
                        setNeedPauseLocalVoice(false);
                        OnlineMusicManager.getManager().requestToOnLineMusic();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    L.d(TAG, "AudioFocus: AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    L.d(TAG, "AudioFocus: AUDIOFOCUS_LOSS_TRANSIENT");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    L.d(TAG, "AudioFocus: AUDIOFOCUS_GAIN");
                    break;
                default:
                    L.d(TAG, "Unknown audio focus change code");
                    break;
            }
        }
    };

    private OnAudioFocusChangeListener mTempFocusListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    L.d(TAG, "TempFocusListener: AUDIOFOCUS_LOSS");
                    mAudioManager.abandonAudioFocus(mTempFocusListener);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    L.d(TAG, "TempFocusListener: AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mAudioManager.abandonAudioFocus(mTempFocusListener);
                    L.d(TAG, "TempFocusListener: AUDIOFOCUS_LOSS_TRANSIENT");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    L.d(TAG, "TempFocusListener: AUDIOFOCUS_GAIN");
                    break;
                default:
                    L.d(TAG, "TempFocusListener Unknown audio focus change code");
                    break;
            }
        }
    };

    private BroadcastReceiver mAudioReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "onreceive action=" + action);
            if (Constant.ACTION_NAVI_ONE_VOICEPROTOCOL.equals(action)) {
                onCldNaviTtsStatusChanged(intent);
            } else if (Constant.ACTION_AUTONAVI_STANDARD_BROADCAST_SEND.equals(action)) {
                onAutoNaviTtsStatusChanged(intent);
            } else if (Constant.ACTION_RECORD_VIEW_HIDED.equals(action)) {
                // resumeMusicWithTxz();
            } else if (Constant.ACTION_RECORD_VIEW_SHOWING.equals(action)) {
                // pauseMusicWithTxz();
            }
        }

    };

    private AudioControl() {
    }

    public static AudioControl getInstance() {
        if (sInstance == null) {
            synchronized (AudioControl.class) {
                if (sInstance == null) {
                    sInstance = new AudioControl();
                }
            }
        }

        return sInstance;
    }

    public void init() {
        mAudioManager = (AudioManager) SpeakApp.getApp().getSystemService(Context.AUDIO_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_NAVI_ONE_VOICEPROTOCOL);
        filter.addAction(Constant.ACTION_RECORD_VIEW_HIDED);
        filter.addAction(Constant.ACTION_RECORD_VIEW_SHOWING);
        filter.addAction(Constant.ACTION_AUTONAVI_STANDARD_BROADCAST_SEND);
        SpeakApp.getApp().registerReceiver(mAudioReceiver, filter);
    }

    public synchronized void setNeedPauseLocalVoice(boolean needPause) {
        L.d(TAG, "setNeedPauseLocalVoice: " + needPause);
        mNeedPauseLocalVoice = needPause;
    }

    public synchronized boolean needPauseLocalVoice() {
        L.d(TAG, "needPauseLocalVoice: " + mNeedPauseLocalVoice);
        return mNeedPauseLocalVoice;
    }

    private void onCldNaviTtsStatusChanged(Intent intent) {
        String voiceProtocol = intent.getStringExtra(Constant.VOICE_PROTOCOL);
        if (Constant.VOICE_PROTOCOL_PLAY.equals(voiceProtocol)) {
            pauseMusicWhileNaviTtsStart();
        } else if (Constant.VOICE_PROTOCOL_STOP.equals(voiceProtocol)) {
            resumeMusicWhileNaviTtsEnd();
        }
    }

    private void onAutoNaviTtsStatusChanged(Intent intent) {
        int keyType = intent.getIntExtra("KEY_TYPE", -1);
        if (keyType == 10019) {
            int state = intent.getIntExtra("EXTRA_STATE", -1);
            L.d(TAG, "receive state = " + state);
            if (state == Constant.AUTONAVI_TTS_BEGIN) {
                pauseMusicWhileNaviTtsStart();
            } else if (state == Constant.AUTONAVI_TTS_END) {
                resumeMusicWhileNaviTtsEnd();
            }
        }
    }

    private synchronized void pauseMusicWhileNaviTtsStart() {
        L.d(TAG, "pauseMusic begin sCount=" + mNaviPauseMusicCount);
        mNaviPauseMusicCount++;
        L.d(TAG, "pauseMusic end sCount=" + mNaviPauseMusicCount);
        if (mNaviPauseMusicCount > 1) {
            return;
        }

        McuServiceManager.getInstance().startNaviVoice();
    }

    public synchronized void resumeMusicWhileNaviTtsEnd() {
        L.d(TAG, "resumeMusic begin sCount=" + mNaviPauseMusicCount);
        mNaviPauseMusicCount--;

        mNaviPauseMusicCount = mNaviPauseMusicCount < 0 ? 0 : mNaviPauseMusicCount;
        L.d(TAG, "resumeMusic end sCount=" + mNaviPauseMusicCount);
        if (mNaviPauseMusicCount > 0) {
            return;
        }
        McuServiceManager.getInstance().stopNaviVoice();
    }

    private void pauseTXZMusicInner() {
        mAudioManager.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }

    private void resumeTXZMusicInner() {
        if (mHasPauseMusicWithTxz) {
            return;
        }

        if (mNaviPauseMusicCount > 0) {
            return;
        }
        mAudioManager.abandonAudioFocus(mAudioFocusListener);
    }

    public void pauseTXZMusicTransient() {
        mAudioManager.requestAudioFocus(mTempFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }

    public void pauseTXZMusic() {
        mAudioManager.requestAudioFocus(mTempFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
    }

    public void resumeTXZMusic() {
        mAudioManager.abandonAudioFocus(mTempFocusListener);
    }

    public void pauseMusicWithTxz() {
        L.d(TAG, "pauseMusicWithTxz mHasPauseMusicWithTxz=" + mHasPauseMusicWithTxz);
        if (mHasPauseMusicWithTxz) {
            return;
        }
        mHasPauseMusicWithTxz = true;
//        pauseTXZMusicInner();
        McuServiceManager.getInstance().startVoiceCtrol();
    }

    public void resumeMusicWithTxz() {
        L.d(TAG, "resumeMusicWithTxz ");
        mHasPauseMusicWithTxz = false;
        McuServiceManager.getInstance().stopVoiceCtrol();
//        resumeTXZMusicInner();
    }

    public synchronized void resetVoiceCount() {
        L.d(TAG, "resetVoiceCount...");
        mNaviPauseMusicCount = 0;
        mHasPauseMusicWithTxz = false;
    }

    public synchronized void resetTxzStatus() {
        mHasPauseMusicWithTxz = false;
        McuServiceManager.getInstance().stopVoiceCtrol();
    }

    private synchronized void resetNaviStatus() {
        mNaviPauseMusicCount = 0;
        McuServiceManager.getInstance().stopNaviVoice();
    }

    public void reset() {
        resetTxzStatus();
        resetNaviStatus();
    }

}
