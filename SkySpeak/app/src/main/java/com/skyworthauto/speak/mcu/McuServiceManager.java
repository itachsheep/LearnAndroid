package com.skyworthauto.speak.mcu;

import com.skyworthauto.sdk.define.McuCmdType;
import com.skyworthauto.sdk.manager.mcu.SkyBaseManager;
import com.skyworthauto.speak.util.L;

public class McuServiceManager extends SkyBaseManager {

    private static final String TAG = McuServiceManager.class.getSimpleName();

    private static McuServiceManager mManager;

    protected McuServiceManager() {
    }

    public static McuServiceManager getInstance() {
        synchronized (McuServiceManager.class) {
            if (null == mManager) {
                mManager = new McuServiceManager();
            }

            return mManager;
        }
    }

    public boolean isInOnlineMusicSource() {
        return getCurrentSource() == McuCmdType.SOURCE_ONLINE_MUSIC;
    }

    public boolean isInVideoSource() {
        return getCurrentSource() == McuCmdType.SOURCE_MP4;
    }

    public boolean isInBtMusicSource() {
        return getCurrentSource() == McuCmdType.SOURCE_BT_MUSIC;
    }

    public boolean isInLocalMusicSource() {
        return getCurrentSource() == McuCmdType.SOURCE_MP3;
    }

    public void startNaviVoice() {
        mManager.naviVoiceStart();
    }

    public void stopNaviVoice() {
        mManager.naviVoiceStop();
    }

    public void startVoiceCtrol() {
        mManager.voiceCtrolStart();
    }

    public void stopVoiceCtrol() {
        mManager.voiceCtrolStop();
    }

    //    private ISkyMcuService getMcuService() {
    //        if (null == sSkyMcuServiceManager) {
    //            return null;
    //        }
    //        return sSkyMcuServiceManager.getMcuService();
    //    }

    public void switchToAndroidSurface(final int surfaceId) {
        sendToMcu(McuCmdType.SYSTEM_STATUS_TYPE,
                McuCmdType.SYSTEM_STATUS_CID.CURRENT_WORK_SURFACE.ordinal(), new int[]{surfaceId});
    }

    @Override
    protected void initManager() {

    }

    @Override
    protected int getSelfModeFid() {
        return 0;
    }
}
