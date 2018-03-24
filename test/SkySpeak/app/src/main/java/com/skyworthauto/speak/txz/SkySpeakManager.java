package com.skyworthauto.speak.txz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

import com.skyworthauto.sdk.define.SkyBroadcast;
import com.skyworthauto.speak.Config;
import com.skyworthauto.speak.R;
import com.skyworthauto.speak.cmd.CmdProviderFactory;
import com.skyworthauto.speak.cmd.CustomCmdManager;
import com.skyworthauto.speak.cmd.GlobalCmdManager;
import com.skyworthauto.speak.cmd.RadioControl;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.GpsListener;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.SkyPreferenceManager;
import com.skyworthauto.speak.util.Utils;
import com.txznet.sdk.TXZAsrManager;
import com.txznet.sdk.TXZCallManager;
import com.txznet.sdk.TXZConfigManager;
import com.txznet.sdk.TXZConfigManager.ActiveListener;
import com.txznet.sdk.TXZConfigManager.ConnectListener;
import com.txznet.sdk.TXZConfigManager.InitListener;
import com.txznet.sdk.TXZConfigManager.InitParam;
import com.txznet.sdk.TXZMusicManager;
import com.txznet.sdk.TXZNavManager;
import com.txznet.sdk.TXZPowerManager;
import com.txznet.sdk.TXZSenceManager;
import com.txznet.sdk.TXZSenceManager.SenceType;
import com.txznet.sdk.TXZStatusManager;
import com.txznet.sdk.TXZTtsManager;

public class SkySpeakManager implements InitListener, ActiveListener {

    private static final String KEY_TXZ_MUSIC_IS_PLAYING_WHILE_ACC_OFF = "key_txz_music_is_playing";

    private static final int TIMEOUT = 10 * 1000;

    public static final String TAG = SkySpeakManager.class.getSimpleName();

    private static final String ACTION_OPEN_TXZ_CTRL_VIEW = SkyBroadcast.START_SPEAKING_CTROL_VIEW;
    private static final String ACTION_POWER_SLEEP = "android.intent.need.openairplane";
    // SkyBroadcast.SKY_SYS_START_ACC_OFF
    private static final String ACTION_POWER_WAKEUP = "android.intent.need.closeairplane";
    // SkyBroadcast.SKY_SYS_START_ACC_ON

    private static final String ACTION_SPEAK_STATE_CHANGE = "com.skyworthauto.state.txz.running";
    private static final String STATUS = "status";
    private static final int STATUS_CODE_HIDE = 0;
    private static final int STATUS_CODE_SHOW = 1;

    private static final int[] CUSTOM_PIDS =
            new int[]{CmdProviderFactory.ID_CONTROL_MUSIC, CmdProviderFactory.ID_CONTROL_OTHER,
                    CmdProviderFactory.PID_ADAS, CmdProviderFactory.PID_AIR_CONDITION,
                    CmdProviderFactory.PID_AUX, CmdProviderFactory.PID_CAR,
                    CmdProviderFactory.PID_NIGHT_VISION, CmdProviderFactory.PID_PANORAMA,
                    CmdProviderFactory.PID_RADIO, CmdProviderFactory.PID_DVR,
                    CmdProviderFactory.PID_SYSTEM, CmdProviderFactory.PID_PHOTO,
                    CmdProviderFactory.PID_OPEN_APP};

    private static final int[] GLOBAL_PIDS =
            new int[]{CmdProviderFactory.ID_CONTROL_MUSIC, CmdProviderFactory.PID_PHOTO};

    private Context mContext;

    private SkyStatusListener mStatusListener;
    private INaviTool mSkyNavTool;
    private SkySenceTool mSkySenceTool;
    private SkyBluetoothCallTool mSkyCallTool;
    private GpsListener mGpsListener;

    private boolean mIsInitSuccess = false;
    private boolean mIsPowerSleeped = false;

    private final WakeupSetting mWakeupSetting;

    private ConnectListener mConnectListener = new ConnectListener() {

        @Override
        public void onConnect() {
            L.d(TAG, "onConnect xxxxxxxxxxxx");
        }

        @Override
        public void onDisconnect() {
            L.d(TAG, "onDisconnect xxxxxxxxxx");
            AudioControl.getInstance().resetTxzStatus();
        }

        @Override
        public void onExcepiton() {
            L.d(TAG, "onExcepiton xxxxxxxxxxx");
            AudioControl.getInstance().resetTxzStatus();
        }

    };

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "onReceive action=" + action);

            if (ACTION_OPEN_TXZ_CTRL_VIEW.equals(action)) {
                startCtrlView();
            } else if (Constant.ACTION_RECORD_VIEW_SHOWING.equals(action)) {
                unregisterGlobalCmd();
            } else if (Constant.ACTION_RECORD_VIEW_HIDED.equals(action)) {
                registerGlobalCmd();
            } else if (ACTION_POWER_WAKEUP.equals(action)) {
                onPowerWakeup();
            } else if (ACTION_POWER_SLEEP.equals(action)) {
                onPowerSleep();
            } else if (SkyBroadcast.SKY_SYS_START_ACC_OFF.equals(action)) {
                pauseTXZMusicTransient();
            } else if (SkyBroadcast.SKY_SYS_START_ACC_ON.equals(action)) {
                powerWakeup();
                resumeTXZMusic();

                // add for bug QRT-242, after sleep the bind lost, need reset
                //                OnlineMusicManager.onResume();
            }
        }

    };

    public SkySpeakManager(Context context) {
        mContext = context;
        mStatusListener = new SkyStatusListener();
        initNaviTool();

        if (!Config.isInvalidBt()) {
            mSkyCallTool = new SkyBluetoothCallTool(mContext);
        }

        mGpsListener = new GpsListener();
        mSkySenceTool = new SkySenceTool();
        mWakeupSetting = new WakeupSetting(mContext);
    }

    private void initNaviTool() {
        if (Config.useSkyNavi()) {
            mSkyNavTool = new SkyNavTool(mContext);
        } else if (Config.useCldNavi()) {
            mSkyNavTool = new CldNavTool(mContext);
        }
    }

    public void init() {
        mGpsListener.requestLocationUpdates();
        AudioControl.getInstance().init();

        initTXZ();
        registerReceiver();

        mWakeupSetting.initWakeupStates();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OPEN_TXZ_CTRL_VIEW);
        filter.addAction(Constant.ACTION_RECORD_VIEW_SHOWING);
        filter.addAction(Constant.ACTION_RECORD_VIEW_HIDED);
        filter.addAction(ACTION_POWER_WAKEUP);
        filter.addAction(ACTION_POWER_SLEEP);
        filter.addAction(SkyBroadcast.SKY_SYS_START_ACC_OFF);
        filter.addAction(SkyBroadcast.SKY_SYS_START_ACC_ON);
        mContext.registerReceiver(mBroadcastReceiver, filter);
    }

    private void initTXZ() {
        InitParam initParam = createInitParam();

        L.d(TAG, "TXZConfigManager initialize");
        L.d(TAG, "getIMEI=" + Utils.getIMEI());

        TXZConfigManager.getInstance().setConnectListener(mConnectListener);
        TXZConfigManager.getInstance().initialize(mContext, initParam, this, this);

        TXZConfigManager.getInstance().showHelpInfos(Config.needShowHelpInfos());
        TXZConfigManager.getInstance().setSelectListTimeout(TIMEOUT);
        TXZStatusManager.getInstance().addStatusListener(mStatusListener);

        TXZStatusManager.getInstance().setAudioFocusLogic(new Runnable() {

            @Override
            public void run() {
                L.d(TAG, "onRequestAudioFocus !!!!!!!!!!!!");
                AudioControl.getInstance().pauseMusicWithTxz();
            }
        }, new Runnable() {

            @Override
            public void run() {
                L.d(TAG, "onAbandonAudioFocus !!!!!!!!!!!!");
                AudioControl.getInstance().resumeMusicWithTxz();
            }
        });
    }

    private InitParam createInitParam() {
        InitParam initParam;

        // TODO 获取接入分配的appId和appToken
        String appId = mContext.getResources().getString(R.string.txz_sdk_init_app_id);
        String appToken = mContext.getResources().getString(R.string.txz_sdk_init_app_token);
        // TODO 设置初始化参数
        initParam = new InitParam(appId, appToken);
        // TODO 可以设置自己的客户ID，同行者后台协助计数/鉴权
        // mInitParam.setAppCustomId("ABCDEFG");
        // TODO 可以设置自己的硬件唯一标识码
        // mInitParam.setUUID("0123456789");

        // TODO 设置识别和tts引擎类型
        // initParam.setAsrType(AsrEngineType.ASR_YUNZHISHENG).setTtsType(
        // TtsEngineType.TTS_YUNZHISHENG);

        // TODO 设置唤醒词
        String[] wakeupKeywords =
                mContext.getResources().getStringArray(R.array.txz_sdk_init_wakeup_keywords);
        initParam.setWakeupKeywordsNew(wakeupKeywords);

        // TODO 可以按需要设置自己的对话模式
        // mInitParam.setAsrMode(AsrMode.ASR_MODE_CHAT);
        // TODO 设置识别模式，默认自动模式即可
        // mInitParam.setAsrServiceMode(AsrServiceMode.ASR_SVR_MODE_AUTO);
        // TODO 设置是否允许启用服务号
        // mInitParam.setEnableServiceContact(true);
        // TODO 设置开启回音消除模式
        if (!Config.isInnerBt()) {
            initParam.setFilterNoiseType(1);
            initParam.forceStopWkWhenTts(true);
        }

        // 正常唤醒阀值,建议值为 -2.7f 到 -3.1f 分数越低，越容易唤醒，但是误唤醒率越高
        initParam.setWakeupThreshhold(-3.2f);
        // initParam.setUseHQualityWakeupModel(true);

        // 隐藏图标
        // initParam.setFloatToolType(FloatToolType.FLOAT_NONE);
        return initParam;
    }

    @Override
    public void onError(int code, String reason) {
        // TODO 初始化出错
        L.d(TAG, "initialize.onError code=" + code + ", reason=" + reason);
    }

    @Override
    public void onFirstActived() {
        // TODO 首次联网激活，如果需要出厂激活提示，可以在这里完成
        L.d(TAG, "initialize.onFirstActived");
    }

    @Override
    public void onSuccess() {
        // TODO 初始化成功，可以在这里根据需要执行一些初始化操作，参考其他Activity
        // TODO 设置一些参数(参考ConfigActivity)
        // TODO 注册指令(参考AsrActivity)
        // TODO 设置电话(参考CallActivity)、音乐(参考MusicActivity)、导航(参考NavActivity)工具
        // TODO 同步联系人(参考CallActivity)
        L.d(TAG, "initialize.onSuccess");
        mIsInitSuccess = true;

        // TXZTtsManager.getInstance().speakText("同行者引擎初始化成功");
        setTXZTools();
        registerGlobalCmd();
        registerCustomCmd();
        updateSpeakIcon(true);
    }

    private void setTXZTools() {
        TXZTtsManager.getInstance().setDefaultAudioStream(AudioManager.STREAM_MUSIC);

        if (mSkyNavTool != null) {
            TXZNavManager.getInstance().setNavTool(mSkyNavTool);
        }

        TXZMusicManager.getInstance().setNotOpenAppPName(
                new String[]{Constant.PACKAGE_NAME_CLD_NAVI_QR200,
                        Constant.PACKAGE_NAME_CLD_NAVI_QR210});

        TXZMusicManager.getInstance().setNeedAsr(false);
        TXZMusicManager.getInstance().setIsCloseVolume(true);

        TXZSenceManager.getInstance().setSenceTool(SenceType.SENCE_TYPE_ALL, mSkySenceTool);
        TXZSenceManager.getInstance().setSenceTool(SenceType.SENCE_TYPE_COMMAND, mSkySenceTool);
        TXZSenceManager.getInstance().setSenceTool(SenceType.SENCE_TYPE_MUSIC, mSkySenceTool);

        setCallTool();
    }

    private void setCallTool() {
        if (mSkyCallTool != null) {
            TXZCallManager.getInstance().setCallTool(mSkyCallTool);
            mSkyCallTool.syncContacts();
        }
    }

    private void updateSpeakIcon(boolean visiable) {
        Intent intent = new Intent(ACTION_SPEAK_STATE_CHANGE);
        intent.putExtra(STATUS, visiable ? STATUS_CODE_SHOW : STATUS_CODE_HIDE);
        mContext.sendBroadcast(intent);
    }

    public void registerGlobalCmd() {
        for (int i = 0; i < GLOBAL_PIDS.length; i++) {
            GlobalCmdManager.getInstance().registerCmd(GLOBAL_PIDS[i]);
        }
    }

    public void unregisterGlobalCmd() {
        for (int i = 0; i < GLOBAL_PIDS.length; i++) {
            GlobalCmdManager.getInstance().unregisterCmd(GLOBAL_PIDS[i]);
        }
    }

    private void registerCustomCmd() {
        for (int i = 0; i < CUSTOM_PIDS.length; i++) {
            CustomCmdManager.getInstance().registerCmd(CUSTOM_PIDS[i]);
        }

        RadioControl.registerAM();
        RadioControl.registerFM();
    }

    private void unregisterCustomCmd() {
        for (int i = 0; i < CUSTOM_PIDS.length; i++) {
            CustomCmdManager.getInstance().unregisterCmd(CUSTOM_PIDS[i]);
        }
    }

    private void pauseTXZMusicTransient() {
        if (isTxzMusicPlaying()) {
            setTxzMusicNeedRestart(true);
            AudioControl.getInstance().pauseTXZMusicTransient();
        } else {
            setTxzMusicNeedRestart(false);
        }
    }

    private boolean isTxzMusicPlaying() {
        try {
            return TXZMusicManager.getInstance().isPlaying();
        } catch (Exception e) {
            return false;
        }
    }

    private void resumeTXZMusic() {
        if (needRestartTxzMusic()) {
            AudioControl.getInstance().resumeTXZMusic();
            setTxzMusicNeedRestart(false);
        }
    }

    private void setTxzMusicNeedRestart(boolean needRestart) {
        SkyPreferenceManager.putBoolean(KEY_TXZ_MUSIC_IS_PLAYING_WHILE_ACC_OFF, needRestart);
    }

    private boolean needRestartTxzMusic() {
        return SkyPreferenceManager.getBoolean(KEY_TXZ_MUSIC_IS_PLAYING_WHILE_ACC_OFF, false);
    }

    private void startCtrlView() {
        TXZAsrManager.getInstance().triggerRecordButton();
    }

    private void onPowerSleep() {
        AudioControl.getInstance().resetVoiceCount();
        mGpsListener.removeLocationUpdates();
        powerSleep();
    }

    private void onPowerWakeup() {
        powerWakeup();
        mGpsListener.requestLocationUpdates();
    }

    private void powerSleep() {
        if (mIsPowerSleeped) {
            return;
        }

        L.d(TAG, "powerSleep");
        mIsPowerSleeped = true;
        TXZPowerManager.getInstance()
                .notifyPowerAction(TXZPowerManager.PowerAction.POWER_ACTION_BEFORE_SLEEP);
        TXZPowerManager.getInstance().releaseTXZ();
    }

    private void powerWakeup() {
        if (mIsPowerSleeped) {
            L.d(TAG, "powerWakeup");
            mIsPowerSleeped = false;
            TXZPowerManager.getInstance().reinitTXZ(new Runnable() {
                @Override
                public void run() {
                    TXZPowerManager.getInstance()
                            .notifyPowerAction(TXZPowerManager.PowerAction.POWER_ACTION_WAKEUP);
                }
            });
        }
    }

    public void exit() {
        mIsInitSuccess = false;
        TXZStatusManager.getInstance().removeStatusListener(mStatusListener);

        if (mSkyCallTool != null) {
            mSkyCallTool.exit(mContext);
        }
        if (mSkyNavTool != null) {
            mSkyNavTool.exit();
        }
        mSkySenceTool.exit();
        mStatusListener.exit();

        unregisterGlobalCmd();
        unregisterCustomCmd();

        onPowerSleep();
        mContext.unregisterReceiver(mBroadcastReceiver);
    }

}
