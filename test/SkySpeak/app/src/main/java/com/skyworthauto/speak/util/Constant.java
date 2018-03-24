package com.skyworthauto.speak.util;

import android.net.Uri;
import android.provider.ContactsContract;

import com.skyworthauto.sdk.define.SkyBroadcast;

public class Constant {

    public static final String ACTION_VOICE_CTRL_PLAYBACK =
            "com.skyworthauto.voice.ctrl.music.playback";
    public static final String EXTRA_MEDIA_PLAYBACK_TYPE = "media_control_type"; // （ 媒体控制类型） 0：重复
    // 1.播放/暂停 2.上下曲
    public static final int PLAYBACK_TYPE_REPEAT = 0;
    public static final int PLAYBACK_TYPE_PLAY = 1;
    public static final int PLAYBACK_TYPE_TRACK = 2;

    public static final String EXTRA_PLAYBACK_REPEAT = "media_repeat"; // （重复）0：顺序循环，1：单曲循环，2：随机播放
    public static final int REPEAT_SEQUENCE = 0;
    public static final int REPEAT_SINGLE = 1;
    public static final int REPEAT_RANDOM = 2;

    public static final String EXTRA_PLAYBACK_PLAY = "media_play"; // （播放/暂停）0：播放，1：暂停
    public static final int PLAY_PLAY = 0;
    public static final int PLAY_PAUSE = 1;

    public static final String EXTRA_PLAYBACK_TRACK = "media_track"; // （上下曲）0：上一首，1：下一首
    public static final int TRACK_PREV = 0;
    public static final int TRACK_NEXT = 1;

    public static final String ACTION_NAVI_ONE_ACTIVITY_STATUS_CHANGED =
            "android.NaviOne.ActivityStatusBroadcast";
    public static final String STATUS = "Status";
    public static final String FOREGROUND = "Foreground";
    public static final String BACKGROUND = "Background";

    public static final String ACTION_NAVI_ONE_VOICEPROTOCOL = "android.NaviOne.voiceprotocol";
    public static final String VOICE_PROTOCOL = "VOICEPROTOCOL";
    public static final String VOICE_PROTOCOL_PLAY = "play";
    public static final String VOICE_PROTOCOL_STOP = "stop";

    public static final String ACTION_AUTONAVI_STANDARD_BROADCAST_SEND =
            "AUTONAVI_STANDARD_BROADCAST_SEND";
    public static final int AUTONAVI_TTS_BEGIN = 13;
    public static final int AUTONAVI_TTS_END = 14;

    public static final String ACTION_ENTER_NAVI_ONE = "android.NaviOne.AutoStartReceiver";
    public static final String ACTION_EXIT_NAVI_ONE = "android.NaviOne.AutoExitReceiver";

    public static final String ACTION_RECORD_VIEW_SHOWING = "com.txznet.txz.record.show";
    public static final String ACTION_RECORD_VIEW_HIDED = "com.txznet.txz.record.dismiss";

    public static final int NAV_SURFACE_ID = 7;

    public static final String RO_PRODUCT_SKYBLUETOOTH = "ro.product.skybluetooth";
    public static final String INNER = "inner";
    public static final String OUTSIDE = "outside";
    public static final String INVALID = "invalid";

    public static final String ACTION_ON_ACTIVITY_START = SkyBroadcast.SKY_ACTIVITY_START;
    public static final String ACTIVITY_INFO = "sky_start_activity_info";

    public static final Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    public static final String EMPTY = "";
    public static final String PACKAGE_NAME_CLD_NAVI_QR200 = "cld.navi.c3826.mainframe";
    public static final String PACKAGE_NAME_CLD_NAVI_QR210 = "cld.navi.c3951.mainframe";
    public static final String PACKAGE_NAME_TXZ_MUSIC = "com.txznet.music";

    public static final String ACTION_TXZ_BEGIN_TTS = "com.skyworthauto.speak.txz.begin.tts";
    public static final String ACTION_SOURCE_CHANGE = "com.skyworthauto.mcu.source.change";

    public static final String KEY_SPEAK_WAKEUP_SWITCH = "sky_speak_wakeup_switch";
    public static final int WAKEUP_ENABLE = 1;
    public static final int WAKEUP_UNABLE = 0;

    public static final String KEY_SPEAK_WAKEUP_WORD = "sky_speak_wakeup_word";

}
