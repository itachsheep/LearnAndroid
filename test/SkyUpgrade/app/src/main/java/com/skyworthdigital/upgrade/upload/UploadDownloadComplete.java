package com.skyworthdigital.upgrade.upload;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.port.CheckUpgradeResult;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;
import com.skyworthdigital.upgrade.utils.RequestUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class UploadDownloadComplete {
    private UpgradeTask mTask = null;
    private Callback.Cancelable cancelable = null;

    public UploadDownloadComplete(UpgradeTask task) {
        mTask = task;
    }

    public void uploadDownloadResult() {
        if (!mTask.isUploadDownloadComplete()) {
            cancelable = RequestUtil.sendRequest(RequestUtil.getUploadDownloadUrl(), baseJson(), callback);
        }
    }

    Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {

        @Override
        public void onCancelled(CancelledException arg0) {
            LogUtil.i("onCancelled");
        }

        @Override
        public void onError(Throwable throwable, boolean flag) {
            throwable.printStackTrace();
            LogUtil.i("onError : " + throwable.toString());
        }

        @Override
        public void onFinished() {
            LogUtil.i("onFinished");
        }

        @Override
        public void onSuccess(String result) {
            LogUtil.i("onSuccess : " + result);
            Gson sgon = new Gson();
            CheckUpgradeResult up = sgon.fromJson(result, CheckUpgradeResult.class);

            LogUtil.i("onSuccess : " + up);
            if (up.getCode() == 0) {
                UpgradeTask lastTask = UpgradeApp.getInstance().getTaskManager().obtainLastTask();
                if (lastTask.isMD5right()) {
                    lastTask.setUploadDownloadComplete(true);
                    UpgradeApp.getInstance().getTaskManager().updateObj(lastTask);
                }
            }
        }
    };

    private JSONObject baseJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("did", CommonUtil.getDeviceNo());
            json.put("hardversion", CommonUtil.getHardVersion());
            json.put("version", CommonUtil.getSoftVersion());
            json.put("devicetype", CommonUtil.getDeviceType());
            json.put("vendor", CommonUtil.getVendor());
            String sn = CommonUtil.getSnno();
            json.put("snno", sn);
            json.put("customerid", CommonUtil.getCustomerid(sn));
            json.put("pkgurl", mTask.getPkgurl());
            json.put("pkgversion", mTask.getPkgversion());
            json.put("pkgID", mTask.getUpgradeInfoId());
            json.put("presoft", mTask.getPresoft());
            json.put("ismd5right", mTask.isMD5right());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
