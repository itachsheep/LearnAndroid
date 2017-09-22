package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.mcu.OnlineMusicManager;
import com.skyworthauto.speak.txz.AudioControl;
import com.skyworthauto.speak.util.L;

public class OpenActivity extends CmdSpeakable {

    private static final String TAG = "OpenActivity";

    private int[] NAME_LIST = new int[]{R.string.carcorder_package_name};

    protected String mPackageName;
    protected String mClassName;
    protected int mSurfaceId;

    public OpenActivity(Context context, int cmdArrId, int cmdId, int speakId, int surfaceId,
            int packageNameId, int classNameId) {
        super(context, cmdArrId, cmdId, speakId);
        Resources resources = context.getResources();
        mPackageName = resources.getString(packageNameId);
        mClassName = resources.getString(classNameId);
        mSurfaceId = resources.getInteger(surfaceId);
    }

    @Override
    public void run() {
        try {
            mContext.startActivity(createIntent());
            switchToAndroidSurface();
        } catch (Exception e) {
            L.e(TAG, "start activity error:" + e.getMessage());
        }
    }

    private void switchToAndroidSurface() {
        if (willSwitchByItself()) {
            return;
        }
        McuServiceManager.getInstance().switchToAndroidSurface(mSurfaceId);
    }

    private boolean willSwitchByItself() {
        for (int nameId : NAME_LIST) {
            if (mPackageName.equals(SpeakApp.getApp().getString(nameId))) {
                return true;
            }
        }
        return false;
    }

    protected Intent createIntent() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(mPackageName, mClassName);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        return intent;
    }

    @Override
    public void onRunBefore() {
        if (mContext.getString(R.string.open_music_id).equals(mCmdKey)) {
            AudioControl.getInstance().pauseTXZMusicTransient();
            OnlineMusicManager.getManager().requestToMp3();
        } else if (mContext.getString(R.string.open_video_id).equals(mCmdKey)) {
            AudioControl.getInstance().pauseTXZMusicTransient();
            OnlineMusicManager.getManager().requestToMp4();
        }
    }

}
