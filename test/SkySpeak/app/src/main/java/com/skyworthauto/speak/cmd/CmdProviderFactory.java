package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.util.SparseArray;

import com.skyworthauto.speak.Config;
import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.cmd.OtherCmds.CloseBluetooth;
import com.skyworthauto.speak.cmd.OtherCmds.CloseMusic;
import com.skyworthauto.speak.txz.AudioControl;
import com.skyworthauto.speak.util.Constant;

import java.util.ArrayList;
import java.util.Collection;

public class CmdProviderFactory {

    public static final int ID_CONTROL_MUSIC = 0;
    public static final int ID_CONTROL_ACTIVITY = 1;
    public static final int ID_CONTROL_NAV = 2;
    public static final int ID_CONTROL_NAV_EX = 3;
    public static final int ID_CONTROL_OTHER = 4;
    public static final int ID_CONTROL_SKY_NAVI = 5;

    public static final int PID_RADIO = 100;
    public static final int PID_AUX = 101;
    public static final int PID_PANORAMA = 102;
    public static final int PID_ADAS = 103;
    public static final int PID_CAR = 104;
    public static final int PID_NIGHT_VISION = 105;
    public static final int PID_AIR_CONDITION = 106;
    public static final int PID_DVR = 107;
    public static final int PID_SYSTEM = 108;
    public static final int PID_MUSIC = 109;
    public static final int PID_VIDEO = 110;
    public static final int PID_PHOTO = 111;
    public static final int PID_OPEN_APP = 112;

    private static SparseArray<ICmdProvider> sProviders = new SparseArray<ICmdProvider>();

    public static ICmdProvider getProvider(int id) {
        ICmdProvider cmdProvider = sProviders.get(id);
        if (null == cmdProvider) {
            cmdProvider = createCmdProvider(id);
            sProviders.put(id, cmdProvider);
        }
        return cmdProvider;
    }

    private static ICmdProvider createCmdProvider(int id) {
        switch (id) {
            case ID_CONTROL_MUSIC:
                return new MusicCmdProvider();

            case ID_CONTROL_ACTIVITY:
            case ID_CONTROL_NAV:
            case ID_CONTROL_NAV_EX:
            case ID_CONTROL_OTHER:
                return new InnerCmdProvider(id);

            case ID_CONTROL_SKY_NAVI:
                return new SkyNaviCmdProvider();

            case PID_RADIO:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.radio_cmd);
            case PID_AUX:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.aux_cmd);
            case PID_PANORAMA:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.panorama_cmd);
            case PID_ADAS:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.adas_cmd);
            case PID_CAR:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.car_cmd);
            case PID_NIGHT_VISION:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.night_vision_cmd);
            case PID_AIR_CONDITION:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.air_condition_cmd);
            case PID_DVR:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.dvr_cmd);
            case PID_SYSTEM:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.system_cmd);
            case PID_MUSIC:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.music_cmd);
            case PID_VIDEO:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.video_cmd);
            case PID_PHOTO:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.photo_cmd);
            case PID_OPEN_APP:
                return CmdProviderInflater.inflate(SpeakApp.getApp(), R.xml.open_app_cmd);

            default:
                throw new NullPointerException("Not found id:" + id);
        }
    }

    private static class InnerCmdProvider implements ICmdProvider {
        private int mId;

        public InnerCmdProvider(int id) {
            mId = id;
        }

        @Override
        public int getId() {
            return mId;
        }

        @Override
        public Collection<? extends CmdSpeakable> getList() {
            switch (mId) {
                case ID_CONTROL_MUSIC:
                    return createMusicCmds(SpeakApp.getApp());
                case ID_CONTROL_ACTIVITY:
                    return createOpenActivityCmds(SpeakApp.getApp());
                case ID_CONTROL_NAV:
                    return createCldNavCmds(SpeakApp.getApp());
                case ID_CONTROL_NAV_EX:
                    return createNavExCmds(SpeakApp.getApp());
                case ID_CONTROL_OTHER:
                    return createOtherCmds(SpeakApp.getApp());
                default:
                    break;
            }
            return null;
        }

        @Override
        public ICommand getCommand(CmdSpeakable cmdData) {
            return new CustomCommand(cmdData);
        }
        
    }

    public static Collection<? extends CmdSpeakable> createMusicCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        cmdList.addAll(createRepeatTypeCmds(context));
        cmdList.addAll(createPlayTypeCmds(context));
        cmdList.addAll(createTrackTypeCmds(context));
        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createRepeatTypeCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        cmdList.add(new ControlMusic(context, R.array.media_repeat_sequence_array,
                R.string.media_repeat_sequence_id, R.string.media_repeat_sequence_speak,
                Constant.PLAYBACK_TYPE_REPEAT, Constant.REPEAT_SEQUENCE));
        cmdList.add(new ControlMusic(context, R.array.media_repeat_single_array,
                R.string.media_repeat_single_id, R.string.media_repeat_single_speak,
                Constant.PLAYBACK_TYPE_REPEAT, Constant.REPEAT_SINGLE));
        cmdList.add(new ControlMusic(context, R.array.media_repeat_random_array,
                R.string.media_repeat_random_id, R.string.media_repeat_random_speak,
                Constant.PLAYBACK_TYPE_REPEAT, Constant.REPEAT_RANDOM));
        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createPlayTypeCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        cmdList.add(new ControlMusic(context, R.array.media_play_array, R.string.media_play_id,
                R.string.media_play_speak, Constant.PLAYBACK_TYPE_PLAY, Constant.PLAY_PLAY));
        cmdList.add(new ControlMusic(context, R.array.media_pause_array, R.string.media_pause_id,
                R.string.media_pause_speak, Constant.PLAYBACK_TYPE_PLAY, Constant.PLAY_PAUSE));
        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createTrackTypeCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        cmdList.add(new ControlMusic(context, R.array.media_track_prev_array,
                R.string.media_track_prev_id, R.string.media_track_prev_speak,
                Constant.PLAYBACK_TYPE_TRACK, Constant.TRACK_PREV));
        cmdList.add(new ControlMusic(context, R.array.media_track_next_array,
                R.string.media_track_next_id, R.string.media_track_next_speak,
                Constant.PLAYBACK_TYPE_TRACK, Constant.TRACK_NEXT));
        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createOpenActivityCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        cmdList.add(new OpenActivity(context, R.array.setting_open_array, R.string.setting_open_id,
                R.string.setting_open_speak, R.integer.setting_surface_id,
                R.string.setting_package_name, R.string.setting_class_name));
        cmdList.add(new OpenActivity(context, R.array.comfort_system_open_array,
                R.string.comfort_system_open_id, R.string.comfort_system_open_speak,
                R.integer.comfort_surface_id, R.string.comfort_system_package_name,
                R.string.comfort_system_class_name));
//        cmdList.add(new OpenActivity(context, R.array.open_music_array, R.string.open_music_id,
//                R.string.open_music_speak, R.integer.music_surface_id, R.string.music_package_name,
//                R.string.music_class_name));
//        cmdList.add(new OpenActivity(context, R.array.open_video_array, R.string.open_video_id,
//                R.string.open_video_speak, R.integer.video_surface_id, R.string.video_package_name,
//                R.string.video_class_name));
        //        cmdList.add(
        //                new OpenActivity(context, R.array.carcorder_open_array, R.string
        // .carcorder_open_id,
        //                        R.string.carcorder_open_speak, R.integer.carcorder_surface_id,
        //                        R.string.carcorder_package_name, R.string.carcorder_class_name));

        if (!Config.isInvalidBt()) {
            cmdList.add(new OpenActivity(context, R.array.bluetooth_open_array,
                    R.string.bluetooth_open_id, R.string.bluetooth_open_speak,
                    R.integer.bluetooth_surface_id, R.string.bluetooth_package_name,
                    R.string.bluetooth_class_name));
        }

        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createCldNavCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        // cmdList.add(new ControlNav(context, R.array.nav_escape_crush_array,
        // R.string.nav_escape_crush_id, 36, ""));
        cmdList.add(new ControlNav(context, R.array.nav_not_run_expressway_array,
                R.string.nav_not_run_expressway_id, R.string.nav_not_run_expressway_speak, 38, ""));
        cmdList.add(
                new ControlNav(context, R.array.nav_charge_less_array, R.string.nav_charge_less_id,
                        R.string.nav_charge_less_speak, 38, ""));
        cmdList.add(new ControlNav(context, R.array.nav_check_whole_journey_array,
                R.string.nav_check_whole_journey_id, R.string.nav_check_whole_journey_speak, 62,
                ""));
        cmdList.add(new ControlNav(context, R.array.nav_zoom_in_array, R.string.nav_zoom_in_id,
                R.string.nav_zoom_in_speak, 26, ""));
        cmdList.add(new ControlNav(context, R.array.nav_zoom_out_array, R.string.nav_zoom_out_id,
                R.string.nav_zoom_out_speak, 27, ""));
        // cmdList.add(new ControlNav(context, R.array.nav_open_road_condition_array,
        // R.string.nav_open_road_condition_id, R.string.nav_open_road_condition_speak, 89,
        // ""));
        // cmdList.add(new ControlNav(context, R.array.nav_close_road_condition_array,
        // R.string.nav_close_road_condition_id, R.string.nav_close_road_condition_speak, 90,
        // ""));
        cmdList.add(new ControlNav(context, R.array.nav_3d_mode_array, R.string.nav_3d_mode_id,
                R.string.nav_3d_mode_speak, 24, ""));
        cmdList.add(new ControlNav(context, R.array.nav_2d_mode_array, R.string.nav_2d_mode_id,
                R.string.nav_2d_mode_speak, 23, ""));
        cmdList.add(new ControlNav(context, R.array.nav_auto_mode_array, R.string.nav_auto_mode_id,
                R.string.nav_auto_mode_speak, 149, ""));
        cmdList.add(new ControlNav(context, R.array.nav_northward_array, R.string.nav_northward_id,
                R.string.nav_northward_speak, 22, ""));
        cmdList.add(new ControlNav(context, R.array.nav_car_head_upwards_array,
                R.string.nav_car_head_upwards_id, R.string.nav_car_head_upwards_speak, 23, ""));
        cmdList.add(new ControlNav(context, R.array.nav_car_light_mode_array,
                R.string.nav_car_light_mode_id, R.string.nav_car_light_mode_speak, 34, ""));
        cmdList.add(new ControlNav(context, R.array.nav_car_night_mode_array,
                R.string.nav_car_night_mode_id, R.string.nav_car_night_mode_speak, 35, ""));

        cmdList.add(new ControlNav(context, R.array.nav_close_array, R.string.nav_close_id,
                R.string.nav_close_speak, 40, "") {
            @Override
            public void run() {
                super.run();
                AudioControl.getInstance().resumeMusicWhileNaviTtsEnd();
            }
        });
        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createNavExCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        cmdList.add(new ControlNavHome(context));
        cmdList.add(new ControlNavCompany(context));
        return cmdList;
    }

    private static Collection<? extends CmdSpeakable> createOtherCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        if (!Config.isInvalidBt()) {
//            cmdList.add(new CloseBluetooth(context));
        }
        cmdList.add(new CloseMusic(context));
        return cmdList;
    }

}
