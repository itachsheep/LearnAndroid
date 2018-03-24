package com.skyworthdigital.upgrade.state.process;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.state.http.CheckUpgradeResult;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;
import com.skyworthdigital.upgrade.util.RequestUtil;

public class UploadDownloadComplete {

    private UpgradeTask mTask = null;
    private Callback.Cancelable cancelable = null;

    public UploadDownloadComplete(UpgradeTask task) {
        mTask = task;
    }

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

    CommonCallback<String> callback = new CommonCallback<String>() {

        @Override
        public void onCancelled(CancelledException arg0) {
            LogUtil.log("onCancelled");
        }

        @Override
        public void onError(Throwable throwable, boolean flag) {
            throwable.printStackTrace();
            LogUtil.log("onError : " + throwable.toString());
        }

        @Override
        public void onFinished() {
            LogUtil.log("onFinished");
        }

        @Override
        public void onSuccess(String result) {
            LogUtil.log("onSuccess : " + result);
            Gson sgon = new Gson();
            CheckUpgradeResult up = sgon.fromJson(result, CheckUpgradeResult.class);

            LogUtil.log("onSuccess : " + up);
            if (up.getCode() == 0) {
                UpgradeTask lastTask = UpgradeApp.getInstance().getTaskManager().obtainLastTask();
                if (lastTask.isMD5right()) {
                    lastTask.setUploadDownloadComplete(true);
                    UpgradeApp.getInstance().getTaskManager().updateObj(lastTask);
                }
            }
        }
    };

    public void uploadDownloadResult() {
        if (!mTask.isUploadDownloadComplete()) {
            cancelable = RequestUtil.sendRequest(RequestUtil.getUploadDownloadUrl(), baseJson(), callback);
        }
    }

    public void cancelUploadDownloadResult() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

}
