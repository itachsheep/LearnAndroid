package com.skyworthauto.speak.txz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.skyworthauto.speak.cmd.CmdProviderFactory;
import com.skyworthauto.speak.cmd.CustomCmdManager;
import com.skyworthauto.speak.cmd.GlobalCmdManager;
import com.skyworthauto.speak.mcu.McuServiceManager;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZNavManager;
import com.txznet.sdk.TXZNavManager.NavToolStatusListener;
import com.txznet.sdk.bean.Poi;

public class CldNavTool implements INaviTool {
    private static final String TAG = CldNavTool.class.getSimpleName();

    private Context mContext;
    private boolean mIsNavForeground;

    private BroadcastReceiver mNaviReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "onreceive action=" + action);
            if (Constant.ACTION_NAVI_ONE_ACTIVITY_STATUS_CHANGED.equals(action)) {
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
        String state = intent.getStringExtra(Constant.STATUS);
        if (Constant.FOREGROUND.equals(state)) {
            L.d(TAG, "activity foreground");
            mIsNavForeground = true;
            McuServiceManager.getInstance().switchToAndroidSurface(Constant.NAV_SURFACE_ID);
            registerGlobalCmd();
            registerCustomCmd();
        } else if (Constant.BACKGROUND.equals(state)) {
            L.d(TAG, "activity background");
            mIsNavForeground = false;
            unregisterGlobalCmd();
            unregisterCustomCmd();
        }
    }

    private void registerCustomCmd() {
        CustomCmdManager.getInstance().registerCmd(CmdProviderFactory.ID_CONTROL_NAV);
    }

    private void unregisterCustomCmd() {
        CustomCmdManager.getInstance().unregisterCmd(CmdProviderFactory.ID_CONTROL_NAV);
    }

    private void registerGlobalCmd() {
        GlobalCmdManager.getInstance().registerCmd(CmdProviderFactory.ID_CONTROL_NAV);
        GlobalCmdManager.getInstance().registerCmd(CmdProviderFactory.ID_CONTROL_NAV_EX);
    }

    private void unregisterGlobalCmd() {
        GlobalCmdManager.getInstance().unregisterCmd(CmdProviderFactory.ID_CONTROL_NAV);
        GlobalCmdManager.getInstance().unregisterCmd(CmdProviderFactory.ID_CONTROL_NAV_EX);
    }

    public CldNavTool(Context context) {
        mContext = context;

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_NAVI_ONE_ACTIVITY_STATUS_CHANGED);
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
        double longitude = poi.getLng();
        double latitude = poi.getLat();
        String name = poi.getName();

        if (TextUtils.isEmpty(name)) {
            name = poi.getGeoinfo();
        }

        String locStr = "(" + "TNC11" + "," + "D" + Double.toString(latitude) + "," + Double
                .toString(longitude) + "," + name + ")";

        L.d(TAG, "locStr==" + locStr);

        Intent i = new Intent("android.NaviOne.CldStdTncReceiver");
        i.putExtra("CLDTNC", locStr);
        mContext.sendBroadcast(i);
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
        Intent i = new Intent(Constant.ACTION_EXIT_NAVI_ONE);
        mContext.sendBroadcast(i);

        AudioControl.getInstance().resumeMusicWhileNaviTtsEnd();
    }

    @Override
    public void enterNav() {
        L.d(TAG, "enterNav");
        Intent i = new Intent(Constant.ACTION_ENTER_NAVI_ONE);
        i.putExtra("CMD", "Start");
        mContext.sendBroadcast(i);

        McuServiceManager.getInstance().switchToAndroidSurface(Constant.NAV_SURFACE_ID);
    }

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
