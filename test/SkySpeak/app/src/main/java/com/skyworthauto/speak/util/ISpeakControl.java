package com.skyworthauto.speak.util;


import android.content.Intent;

import com.skyworthauto.speak.SpeakApp;

public class ISpeakControl {
    private static final String TAG = "ISpeakControl";

    public static final String EXTRA_ACTION = "extra_action";
    public static final String EXTRA_VALUE = "extra_value";


    /*收音机：
    打开、关闭收音机
    Action："com.skyworthauto.voice.ctrl.radio"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）

    上一个台、下一个台、搜索
    Action："com.skyworthauto.voice.ctrl.radio"
    EXTRA_ACTION:"ctrl_channel"
    EXTRA_VALUE: （int）（0：上一个台 1：下一个台 2：搜索）

    切换到调频、调幅
    Action："com.skyworthauto.voice.ctrl.radio"
    EXTRA_ACTION:"switch_channel"
    EXTRA_VALUE: （int）（0：切到调频 1：切到调幅）

    调频
    Action："com.skyworthauto.voice.ctrl.radio"
    EXTRA_ACTION:"frequency_modulation"
    EXTRA_VALUE:频段（float）

    调幅
    Action："com.skyworthauto.voice.ctrl.radio"
    EXTRA_ACTION:"amplitude_modulation"
    EXTRA_VALUE:幅度（int）*/
    public static final String ACTION_CONTROL_RADIO = "com.skyworthauto.voice.ctrl.radio";

    public static final String CTRL_APP = "ctrl_app";
    public static final int CLOSE = 0;
    public static final int OPEN = 1;

    public static final String CTRL_CHANNEL = "ctrl_channel";
    public static final int PREV_CHANNEL = 0;
    public static final int NEXT_CHANNEL = 1;
    public static final int SEARCH_CHANNEL = 2;

    public static final String SWITCH_CHANNEL = "switch_channel";
    public static final int SWITCH_TO_FM = 0;
    public static final int SWITCH_TO_AM = 1;

    public static final String FREQUENCY_MODULATION = "frequency_modulation";
    public static final String AMPLITUDE_MODULATION = "amplitude_modulation";

    /*蓝牙音乐
    暂停、播放、上一曲、下一曲
    Action："com.skyworthauto.voice.ctrl.bt_music"
    EXTRA_ACTION:"ctrl_music"
    EXTRA_VALUE: （int）（0：暂停 1：播放 2：上一曲 3：下一曲）*/

    public static final String ACTION_CONTROL_BT_MUSIC = "com.skyworthauto.voice.ctrl.bt_music";
    public static final String CTRL_MUSIC = "ctrl_music";
    public static final int PAUSE_MUSIC = 0;
    public static final int PLAY_MUSIC = 1;
    public static final int PREV_MUSIC = 2;
    public static final int NEXT_MUSIC = 3;

    /*蓝牙：
    打开、关闭蓝牙
    Action："com.skyworthauto.voice.ctrl.bluetooth"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）*/
    public static final String ACTION_CONTROL_BT = "com.skyworthauto.voice.ctrl.bluetooth";
//    public static final String CTRL_APP = "ctrl_app";
//    public static final int CLOSE = 0;
//    public static final int OPEN = 1;


    /*AUX
    打开、关闭AUX
    Action："com.skyworthauto.voice.ctrl.aux"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）*/

    public static final String ACTION_CONTROL_AUX = "com.skyworthauto.voice.ctrl.aux";


    /*ADAS
    打开、关闭ADAS
    Action："com.skyworthauto.voice.ctrl.adas"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）

    关闭、打开预警
    Action："com.skyworthauto.voice.ctrl.adas"
    EXTRA_ACTION:"ctrl_early_warning"
    EXTRA_VALUE: （int）（0：关闭预警 1：打开预警）*/

    public static final String ACTION_CONTROL_ADAS = "com.skyworthauto.voice.ctrl.adas";
    public static final String CTRL_EARLY_WARNING = "ctrl_early_warning";

    /*
    DVR
    打开、关闭DVR
    Action："com.skyworthauto.voice.ctrl.dvr"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）

    停止录像、开始录像、拍照、锁定当前录像
    Action："com.skyworthauto.voice.ctrl.dvr"
    EXTRA_ACTION:"ctrl_capture"
    EXTRA_VALUE: （int）（0：停止录像 1：开始录像 2：拍照 3：锁定当前录像）

    回放录像、回放上一首、回放下一首
    Action："com.skyworthauto.voice.ctrl.dvr"
    EXTRA_ACTION:"ctrl_playback_video"
    EXTRA_VALUE: （int）（0：开始回放 1：回放上一首 2：回放下一首）

    回放照片
    Action："com.skyworthauto.voice.ctrl.dvr"
    EXTRA_ACTION:"ctrl_playback_photo"
    EXTRA_VALUE: （int）（0：开始回放 1：回放上一张 2：回放下一张）*/

    public static final String ACTION_CONTROL_DVR = "com.skyworthauto.voice.ctrl.dvr";
    public static final String CTRL_CAPTURE = "ctrl_capture";
    public static final int STOP_RECORD = 0;
    public static final int START_RECORD = 1;
    public static final int TAKE_PHOTO = 2;
    public static final int LOCK_VIDEO = 3;
    public static final String CTRL_PLAYBACK_VIDEO = "ctrl_playback_video";
    public static final String CTRL_PLAYBACK_PHOTO = "ctrl_playback_photo";
    public static final int PLAYBACK_START = 0;
    public static final int PLAYBACK_PREV = 1;
    public static final int PLAYBACK_NEXT = 2;

    /* 360全景
    打开、关闭全景
    Action："com.skyworthauto.voice.ctrl.panorama"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）

    控制方位
    Action："com.skyworthauto.voice.ctrl.panorama"
    EXTRA_ACTION:"ctrl_direction"
    EXTRA_VALUE:（int） （0：前 1：后 2：左 3：右）*/

    public static final String ACTION_CONTROL_PANORAMA = "com.skyworthauto.voice.ctrl.panorama";
    public static final int FRONT = 0;
    public static final int BACK = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    /*车辆控制
    打开、关闭车辆控制
    Action："com.skyworthauto.voice.ctrl.car"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）

    控制顶灯
    Action："com.skyworthauto.voice.ctrl.car"
    EXTRA_ACTION:"ctrl_overhead_light"
    EXTRA_VALUE: （int）（0：关 1：开）

    控制后屏
    Action："com.skyworthauto.voice.ctrl.car"
    EXTRA_ACTION:"ctrl_back_screen"
    EXTRA_VALUE: （int）（0：关 1：开）

    行人提醒
    Action："com.skyworthauto.voice.ctrl.car"
    EXTRA_ACTION:"ctrl_pedestrian_warnning"
    EXTRA_VALUE: （int）（0：关 1：开）

    控制氛围灯
    Action："com.skyworthauto.voice.ctrl.car"
    EXTRA_ACTION:"ctrl_atmosphere_light"
    EXTRA_VALUE: （int）（0：关 1：开）*/

    public static final String ACTION_CONTROL_CAR = "com.skyworthauto.voice.ctrl.car";
    public static final String CTRL_OVERHEAD_LIGHT = "ctrl_overhead_light";
    public static final String CTRL_BACK_SCREEN = "ctrl_back_screen";
    public static final String CTRL_PEDESTRIAN_WARNNING = "ctrl_pedestrian_warnning";
    public static final String CTRL_ATMOSPHERE_LIGHT = "ctrl_atmosphere_light";

    /* 设置
    打开、关闭设置
    Action："com.skyworthauto.voice.ctrl.setting"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）*/

    public static final String ACTION_CONTROL_SETTING = "com.skyworthauto.voice.ctrl.setting";

    /*红外夜视
    打开、关闭红外夜视
    Action："com.skyworthauto.voice.ctrl.night_vision"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）*/

    public static final String ACTION_CONTROL_NIGHT_VISION =
            "com.skyworthauto.voice.ctrl.night_vision";

    /*空调
    打开、关闭空调
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_app"
    EXTRA_VALUE: （int）（0：关 1：开）

    空调模式控制
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_mode"
    EXTRA_VALUE: （int）（0：制冷，1：制热， 2：自然风）

    温度控制
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_temperature"
    EXTRA_VALUE: （int）（0：调低，1：调高，2：直接设置温度）
    EXTRA_TEMPERATURE:(int) （当EXTRA_VALUE=2时设置）

    风量控制
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_speed"
    EXTRA_VALUE: （int）(0:最小，1：调低1级，2：调高1级，3：最大)

    风向控制
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_direction"
    EXTRA_VALUE: （int）（0：吹脸，1：吹脚，2：吹脸吹脚）

    除霜控制
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_defrost"
    EXTRA_VALUE: （int）（0：前档除霜 1：后档除霜）

    循环控制
    Action："com.skyworthauto.voice.ctrl.air_condition"
    EXTRA_ACTION:"ctrl_circulation"
    EXTRA_VALUE: （int）（0：车内循环 1：车外循环 2：车前循环 3：车后循环）*/
    public static final String ACTION_CONTROL_AIR_CONDITION =
            "com.skyworthauto.voice.ctrl.air_condition";
    public static final String CTRL_MODE = "ctrl_mode";
    public static final int REFRIGERATE_MODE = 0;
    public static final int HEATING_MODE = 1;
    public static final int NATURAL_MODE = 2;
    public static final String CTRL_TEMPERATURE = "ctrl_temperature";
    public static final int DECREASE_TEMPERATURE = 0;
    public static final int INCREASE_TEMPERATURE = 1;
    public static final int SETTING_TEMPERATURE = 2;
    public static final String CTRL_SPEED = "ctrl_speed";
    public static final int MIN_SPEED = 0;
    public static final int DECREASE_SPEED = 1;
    public static final int INCREASE_SPEED = 2;
    public static final int MAX_SPEED = 3;
    public static final String CTRL_DIRECTION = "ctrl_direction";
    public static final int BLOW_FACE = 0;
    public static final int BLOW_FOOT = 1;
    public static final int BLOW_FACE_AND_FOOT = 2;
    public static final String CTRL_DEFROST = "ctrl_defrost";
    public static final int DEFROST_FRONT = 0;
    public static final int DEFROST_BACK = 1;
    public static final String CTRL_CIRCULATION = "ctrl_circulation";
    public static final int INNER_CIRCULATION = 0;
    public static final int OUTER_CIRCULATION = 1;
    public static final int FRONT_CIRCULATION = 2;
    public static final int BACK_CIRCULATION = 3;

    /*本地音乐
    打开音乐
    Action："com.skyworthauto.voice.ctrl.sky_music"
    EXTRA_ACTION:"open_music"

    音乐控制
    Action："com.skyworthauto.voice.ctrl.sky_music"
    EXTRA_ACTION:"ctrl_music"
    EXTRA_VALUE: （int）（0：暂停 1：播放 2：上一曲 3：下一曲 4：顺序循环，5：单曲循环，6：随机播放）

    音量控制
    Action："com.skyworthauto.voice.ctrl.sky_music"
    EXTRA_ACTION:"ctrl_volume"
    EXTRA_VALUE: （int）（0：减小音量 1：增大音量）*/

    public static final String ACTION_CONTROL_SKY_MUSIC = "com.skyworthauto.voice.ctrl.sky_music";
    public static final String OPEN_MUSIC = "open_music";
    public static final int SEQUENCE_MUSIC = 4;
    public static final int SINGLE_MUSIC = 5;
    public static final int RANDOM_MUSIC = 6;

    /*public static final String CTRL_MUSIC = "ctrl_music";
    public static final int PAUSE_MUSIC = 0;
    public static final int PLAY_MUSIC = 1;
    public static final int PREV_MUSIC = 2;
    public static final int NEXT_MUSIC = 3;*/

    /*本地视频
    打开视频
    Action："com.skyworthauto.voice.ctrl.sky_video"
    EXTRA_ACTION:"open_video"

    视频控制
    Action："com.skyworthauto.voice.ctrl.sky_video"
    EXTRA_ACTION:"ctrl_video"
    EXTRA_VALUE: （int）（0：暂停 1：播放 2：上一曲 3：下一曲 4：顺序循环，5：单曲循环，6：随机播放）

    音量控制
    Action："com.skyworthauto.voice.ctrl.sky_video"
    EXTRA_ACTION:"ctrl_volume"
    EXTRA_VALUE: （int）（0：减小音量 1：增大音量）*/

    public static final String ACTION_CONTROL_SKY_VIDEO = "com.skyworthauto.voice.ctrl.sky_video";
    public static final String OPEN_VIDEO = "open_video";
    public static final String CTRL_VIDEO = "ctrl_video";
    public static final int PAUSE_VIDEO = 0;
    public static final int PLAY_VIDEO = 1;
    public static final int PREV_VIDEO = 2;
    public static final int NEXT_VIDEO = 3;
    public static final int SEQUENCE_VIDEO = 4;
    public static final int SINGLE_VIDEO = 5;
    public static final int RANDOM_VIDEO = 6;
    public static final String CTRL_VOLUME = "ctrl_volume";
    public static final int DECREASE_VOLUME = 0;
    public static final int INCREASE_VOLUME = 1;

    /*本地图片
    打开图片
    Action："com.skyworthauto.voice.ctrl.sky_photo"
    EXTRA_ACTION:"open_photo"

    图片控制
    Action："com.skyworthauto.voice.ctrl.sky_photo"
    EXTRA_ACTION:"ctrl_photo"
    EXTRA_VALUE: （int）（0：上一张 1：下一张 2：随便看看）*/

    public static final String ACTION_CONTROL_SKY_PHOTO = "com.skyworthauto.voice.ctrl.sky_photo";
    public static final String OPEN_PHOTO = "open_photo";
    public static final String CTRL_PHOTO = "ctrl_photo";
    public static final int PREV_PHOTO = 0;
    public static final int NEXT_PHOTO = 1;
    public static final int RANDOM = 2;

    /*系统设置
    音量控制
    Action："com.skyworthauto.voice.ctrl.system"
    EXTRA_ACTION:"ctrl_volume"
    EXTRA_VALUE: （int）（0：减小音量 1：增大音量 2：静音 3：取消静音 4：最小音量  5：最大音量）

    亮度控制
    Action："com.skyworthauto.voice.ctrl.system"
    EXTRA_ACTION:"ctrl_luminance"
    EXTRA_VALUE: （int）（0：减小亮度 1：增加亮度 ）

    关屏、开屏
    Action："com.skyworthauto.voice.ctrl.system"
    EXTRA_ACTION:"ctrl_screen"
    EXTRA_VALUE: （int）（0：关屏 1：开屏）

    wifi控制
    Action："com.skyworthauto.voice.ctrl.system"
    EXTRA_ACTION:"ctrl_wifi"
    EXTRA_VALUE: （int）（0：关wifi 1：开wifi）

    数据控制
    Action："com.skyworthauto.voice.ctrl.system"
    EXTRA_ACTION:"ctrl_data_network"
    EXTRA_VALUE: （int）（0：关数据 1：开数据）*/

    public static final String ACTION_CONTROL_SYSTEM = "com.skyworthauto.voice.ctrl.system";
    //    public static final String CTRL_VOLUME = "ctrl_volume";
    //    public static final int DECREASE_VOLUME = 0;
    //    public static final int INCREASE_VOLUME = 1;
    public static final int MUTE_VOLUME = 2;
    public static final int UNMUTE_VOLUME = 3;
    public static final int MIN_VOLUME = 4;
    public static final int MAX_VOLUME = 5;

    public static final String CTRL_LUMINANCE = "ctrl_luminance";
    public static final int DECREASE_LUMINANCE = 0;
    public static final int INCREASE_LUMINANCE = 1;

    public static final String CTRL_SCREEN = "ctrl_screen";
    public static final String CTRL_WIFI = "ctrl_wifi";
    public static final String CTRL_DATA_NETWORK = "ctrl_data_network";


    public static void openOrCloseApp(String action, boolean toOpen) {
        Intent intent = createIntent(action, CTRL_APP, toOpen ? OPEN : CLOSE);
        SpeakApp.getApp().sendBroadcast(intent);
    }

    public static Intent createIntent(String action, String subAction) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ACTION, subAction);
        intent.putExtra(EXTRA_VALUE, -1);
        return intent;
    }

    public static Intent createIntent(String action, String subAction, int value) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ACTION, subAction);
        intent.putExtra(EXTRA_VALUE, value);
        return intent;
    }

    public static Intent createIntent(String action, String subAction, float value) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ACTION, subAction);
        intent.putExtra(EXTRA_VALUE, value);
        return intent;
    }

    public static void sentBroadCast(String action, String subAction, int value) {
        L.d(TAG, "action=" + action + ", subAction=" + subAction + ", value=" + value);
        Intent intent = createIntent(action, subAction, value);
        SpeakApp.getApp().sendBroadcast(intent);
    }

}
