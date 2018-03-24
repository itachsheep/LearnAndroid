package com.skyworthdigital.upgrade.util;

import android.content.Context;

import com.skyworthdigital.upload.main.UploadAgent;

public class SkyUploadFlowManager {

    private UploadAgent mUploadAgent;
    private static SkyUploadFlowManager instance;

    public SkyUploadFlowManager() {}

    public static SkyUploadFlowManager getInstance() {
        if (instance == null) {
            instance = new SkyUploadFlowManager();
        }
        return instance;
    }

    public void initUploadAgent(Context context) {
        mUploadAgent = new UploadAgent(context);
    }

    public void destroy(Context context) {
        if (mUploadAgent != null) {
            try {
                mUploadAgent.onDestroy(context);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean saveObject(String type, Object obj) {
        if (mUploadAgent != null) {
            try {
                boolean ret = mUploadAgent.saveObject(type, obj);
                LogUtil.log("saveObject " + type + " " + ret);
                return ret;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean saveString(String type, String jsonInfo) {
        if (mUploadAgent != null) {
            try {
                boolean ret = mUploadAgent.saveString(type, jsonInfo);
                LogUtil.log("saveString " + type + " " + ret);
                return ret;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        LogUtil.log("save object false");
        return false;
    }

}
