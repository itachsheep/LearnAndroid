package com.skyworthauto.speak.txz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.skyworthauto.speak.cmd.CmdProviderFactory;
import com.skyworthauto.speak.cmd.CustomCmdManager;
import com.skyworthauto.speak.cmd.GlobalCmdManager;
import com.skyworthauto.speak.cmd.SkyNaviExecutor;
import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.SkyNaviConstant;
import com.txznet.sdk.TXZNavManager;
import com.txznet.sdk.TXZNavManager.NavToolStatusListener;
import com.txznet.sdk.bean.Poi;

public class SkyNavTool implements INaviTool {
    private static final String TAG = SkyNavTool.class.getSimpleName();

    private Context mContext;
    private boolean mIsNavForeground;
    private SkyNaviExecutor mNaviExecutor;

    private BroadcastReceiver mNaviReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "onreceive action=" + action);
            if (SkyNaviConstant.ACTION_SKY_NAVI_BROADCAST_SEND.equals(action)) {
                onActivityStatusChanges(intent);
            } else if (Constant.ACTION_RECORD_VIEW_HIDED.equals(action)) {
                if (mIsNavForeground) {
                    registerGlobalCmd();
                }
            } else if (Constant.ACTION_RECORD_VIEW_SHOWING.equals(action)) {
                if (mIsNavForeground) {
                    unregisterGlobalCmd();
                }
            }
        }

    };

    private void onActivityStatusChanges(Intent intent) {
        int type = intent.getIntExtra(SkyNaviConstant.KEY_TYPE, -1);
        if (type != SkyNaviConstant.KEY_TYPE_SEND_STATE) {
            return;
        }

        int state = intent.getIntExtra(SkyNaviConstant.EXTRA_STATE, -1);
        if (SkyNaviConstant.STATE_ACTIVITY_FOREGROUND == state) {
            L.d(TAG, "activity foreground");
            mIsNavForeground = true;
            McuServiceManager.getInstance().switchToAndroidSurface(Constant.NAV_SURFACE_ID);
            registerGlobalCmd();
            registerCustomCmd();
        } else if (SkyNaviConstant.STATE_ACTIVITY_BACKGROUND == state) {
            L.d(TAG, "activity background");
            mIsNavForeground = false;
            unregisterGlobalCmd();
            unregisterCustomCmd();
        }
    }

    private void registerCustomCmd() {
        CustomCmdManager.getInstance().registerCmd(CmdProviderFactory.ID_CONTROL_SKY_NAVI);
    }

    private void unregisterCustomCmd() {
        CustomCmdManager.getInstance().unregisterCmd(CmdProviderFactory.ID_CONTROL_SKY_NAVI);
    }

    private void registerGlobalCmd() {
        GlobalCmdManager.getInstance().registerCmd(CmdProviderFactory.ID_CONTROL_SKY_NAVI);
    }

    private void unregisterGlobalCmd() {
        GlobalCmdManager.getInstance().unregisterCmd(CmdProviderFactory.ID_CONTROL_SKY_NAVI);
    }

    public SkyNavTool(Context context) {
        mContext = context;
        mNaviExecutor = new SkyNaviExecutor();

        IntentFilter filter = new IntentFilter();
        filter.addAction(SkyNaviConstant.ACTION_SKY_NAVI_BROADCAST_SEND);
        filter.addAction(Constant.ACTION_RECORD_VIEW_HIDED);
        filter.addAction(Constant.ACTION_RECORD_VIEW_SHOWING);
        context.registerReceiver(mNaviReceiver, filter);
    }

    @Override
    public void setStatusListener(NavToolStatusListener listener) {
        L.d(TAG, "navTool setStatusListener");
    }

    @Override
    public void setStatusListener(
            TXZNavManager.NavToolStatusHighListener navToolStatusHighListener) {

    }

    @Override
    public void navToLoc(Poi poi) {
        L.d(TAG, "navToLoc,poi==" + poi);
        mNaviExecutor.naviWithDest(poi);
    }

    @Override
    public void speakLimitSpeed() {

    }

    @Override
    public void navHome() {
        L.d(TAG, "navHome");
    }

    @Override
    public void navCompany() {
        L.d(TAG, "navCompany");
    }

    @Override
    public boolean isInNav() {
        L.d(TAG, "isInNav");
        return false;
    }

    @Override
    public void exitNav() {
        L.d(TAG, "exitNav");
        mNaviExecutor.closeApp();
        AudioControl.getInstance().resumeMusicWhileNaviTtsEnd();
    }

    @Override
    public void enterNav() {
        L.d(TAG, "enterNav");
        mNaviExecutor.openApp();
        McuServiceManager.getInstance().switchToAndroidSurface(Constant.NAV_SURFACE_ID);
    }

    @Override
    public void exit() {
        if (null != mNaviReceiver) {
            mContext.unregisterReceiver(mNaviReceiver);
        }
    }

    @Override
    public void setCompanyLoc(Poi poi) {
        L.d(TAG, "setCompanyLoc,poi==" + poi);

    }

    @Override
    public void setHomeLoc(Poi poi) {
        L.d(TAG, "setHomeLoc,poi==" + poi);

    }
}
