package com.skyworthdigital.upgrade.state.process;

import android.content.Intent;
import android.os.Handler;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.port.CheckUpgradeResult;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.upload.UploadDownloadComplete;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;
import com.skyworthdigital.upgrade.utils.RequestUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;

import de.greenrobot.event.EventBus;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class UploadUpgradeProcess extends StateProcess {
    private static int commitTimes = 0;
    private Callback.Cancelable cancelable = null;

    public UploadUpgradeProcess(UpgradeTask task) {
        super(task);
    }

    @Override
    public void stopProcess() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    @Override
    public void run() {
        LogUtil.i("UploadUpgradeProcess running");
        UploadDownloadComplete upload = new UploadDownloadComplete(getTask());
        upload.uploadDownloadResult();
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                LogUtil.i("onCancelled");
            }

            @Override
            public void onError(Throwable throwable, boolean flag) {
                throwable.printStackTrace();
                LogUtil.i("UploadUpgradeProcess onError : " + throwable.toString());
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                    public void run() {
                        saveCommitTimes();
                    }

                }, IConstants.SECONDS_5);
            }

            @Override
            public void onFinished() {
                LogUtil.i("UploadUpgradeProcess onFinished");
            }

            @Override
            public void onSuccess(String result) {
                LogUtil.i("UploadUpgradeProcess onSuccess : " + result);
                Gson sgon = new Gson();
                CheckUpgradeResult up = sgon.fromJson(result, CheckUpgradeResult.class);

                LogUtil.i("UploadUpgradeProcess onSuccess : " + up);
                if (up.getCode() == 0) {
                    getTask().setUploadUpgradeResult(true);
                    updateComplete();
                    UploadUpgradeProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                    EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                } else {
                    new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                        public void run() {
                            saveCommitTimes();
                        }

                    }, IConstants.SECONDS_5);
                }
            }
        };

        if (!getTask().isUploadUpgradeResult()) {
            updateLocalUpgradeResult();
            cancelable = RequestUtil.sendRequest(RequestUtil.getUploadUpgradeUrl(), baseJson(), callback);
        }
    }

    private void updateLocalUpgradeResult() {
        getTask().setUpgradeResult(CommonUtil.getUpgradeResult(getTask()));
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
    }

    private void saveCommitTimes() {

        commitTimes++;
        getTask().setUploadUpgradeResultTimes(commitTimes);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
        if (commitTimes >= IConstants.UPLOAD_UPGRADE_RESULT_TIMES) {
            commitTimes = 0;
            updateComplete();
        }

        UploadUpgradeProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
    }

    private void updateComplete() {
        getTask().setState(STATE_COMPLETE);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
        postUpgradeComplete();
    }

    private void postUpgradeComplete() {
        Intent in = new Intent(IConstants.ACTION_UPGRADE_COMPLETE);
        in.putExtra(IConstants.ISUPGRADERESULT, getTask().isUpgradeResult());
        in.putExtra(IConstants.DESCRIPTION, getTask().getPkgcndesc());
        in.putExtra(IConstants.ISFORCEUPGRADE, getTask().getForceupgrade());
        in.putExtra(IConstants.PKGVERSION, getTask().getPkgversion());
        try {
            UpgradeApp.getInstance().sendBroadcast(in);
        }
        catch (Exception e) {
            e.printStackTrace();
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
}
