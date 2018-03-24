package com.skyworthdigital.upgrade.state.process;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;

import android.content.Intent;
import android.os.Handler;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.port.IUpgradeBracast;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.state.http.CheckUpgradeResult;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;
import com.skyworthdigital.upgrade.util.RequestUtil;

import de.greenrobot.event.EventBus;

public class UploadUpgrade extends StateProcess {

    private Callback.Cancelable cancelable = null;
    private static int commitTimes = 0;

    public UploadUpgrade(UpgradeTask task) {
        super(task);
    }

    @Override
    public void run() {
        LogUtil.log("UploadUpgrade running");
        UploadDownloadComplete upload = new UploadDownloadComplete(getTask());
        upload.uploadDownloadResult();
        CommonCallback<String> callback = new CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                LogUtil.log("onCancelled");
            }

            @Override
            public void onError(Throwable throwable, boolean flag) {
                throwable.printStackTrace();
                LogUtil.log("onError : " + throwable.toString());
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                    public void run() {
                        saveCommitTimes();
                    }

                }, 5000);
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
                    getTask().setUploadUpgradeResult(true);
                    updateComplete();
                    UploadUpgrade.this.setStatus(StateProcess.PROCESS_COMPLETE);
                    EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                }
                else {
                    new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                        public void run() {
                            saveCommitTimes();
                        }

                    }, 5000);
                }
            }
        };

        if (!getTask().isUploadUpgradeResult()) {
            updateLocalUpgradeResult();
            cancelable = RequestUtil.sendRequest(RequestUtil.getUploadUpgradeUrl(), baseJson(), callback);
        }
    }

    private void saveCommitTimes() {

        commitTimes++;
        getTask().setUploadUpgradeResultTimes(commitTimes);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
        if (commitTimes >= CommonUtil.UPLOAD_UPGRADE_RESULT_TIMES) {
            commitTimes = 0;
            updateComplete();
        }

        UploadUpgrade.this.setStatus(StateProcess.PROCESS_COMPLETE);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
    }

    @Override
    public void stopProcess() {
        if (cancelable != null) {
            cancelable.cancel();
        }
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
            json.put("upgraderesult", CommonUtil.getUpgradeResult(getTask()));
            json.put("pkgurl", getTask().getPkgurl());
            json.put("pkgversion", getTask().getPkgversion());
            json.put("pkgID", getTask().getUpgradeInfoId());
            json.put("presoft", getTask().getPresoft());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private void updateComplete() {
        getTask().setState(STATE_COMPLETE);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
        postUpgradeComplete();
    }

    private void postUpgradeComplete() {
        Intent in = new Intent(IUpgradeBracast.ACTION_UPGRADE_COMPLETE);
        in.putExtra(CommonUtil.ISUPGRADERESULT, getTask().isUpgradeResult());
        in.putExtra(CommonUtil.DESCRIPTION, getTask().getPkgcndesc());
        in.putExtra(CommonUtil.ISFORCEUPGRADE, getTask().getForceupgrade());
        in.putExtra(CommonUtil.PKGVERSION, getTask().getPkgversion());
        try {
            UpgradeApp.getInstance().sendBroadcast(in);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLocalUpgradeResult() {
        getTask().setUpgradeResult(CommonUtil.getUpgradeResult(getTask()));
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
    }
}
