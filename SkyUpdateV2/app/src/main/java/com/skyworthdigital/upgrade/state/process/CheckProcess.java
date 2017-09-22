package com.skyworthdigital.upgrade.state.process;

import java.util.Locale;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;

import android.content.Intent;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.device.DeviceInfo;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.port.IUpgradeBracast;
import com.skyworthdigital.upgrade.port.UpgradeInfo;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.state.http.CheckUpgradeResult;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;
import com.skyworthdigital.upgrade.util.RequestUtil;
import com.skyworthdigital.upgrade.util.SkyRootUtils;

import de.greenrobot.event.EventBus;

public class CheckProcess extends StateProcess {
    private Callback.Cancelable cancelable = null;

    public CheckProcess() {
        super(null);
    }

    @Override
    public void run() {
        CommonCallback<String> callback = new CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                LogUtil.log("onCancelled");
            }

            @Override
            public void onError(Throwable throwable, boolean flag) {
                throwable.printStackTrace();
                LogUtil.log("onError : " + throwable.toString());
                CheckProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                postNoVersionToView(CommonUtil.MSG_NETWORK_INVALID);
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

                CheckProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);

                if (up.getCode() == 0) {
                    UpgradeInfo info = up.getData().getUpgradeinfo();
                    UpgradeTask lastTask = UpgradeApp.getInstance().getTaskManager().obtainLastTask();
                    
                    if(info.getPkgtype() != CommonUtil.FULL_PACKAGE) {
                        boolean isRoot = SkyRootUtils.isBoxRoot();
                        if(isRoot) {
                            LogUtil.log("box is root");
                            postNoVersionToView(CommonUtil.MSG_SYSTEM_HAS_ROOT);
                            return;
                        }
                    }
                    
                    if (lastTask == null) {
                        LogUtil.log("CheckProcess check first pkg, save and do it! ");
                        saveNewTask(info);
                        postNewVersionToView(info);
                        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                    }
                    else {
                        if (lastTask.getUpgradeInfo().isIDEqual(info)) {
                            if (lastTask.getUpgradeInfo().equals(info)) {
                                LogUtil.log("CheckProcess check pkg is the same, do the curr task! ");
                                if (lastTask.getState() == CheckProcess.STATE_COMPLETE) {
                                    saveNewTask(info);
                                }
                                postNewVersionToView(info);

                                if (lastTask.getState() != CheckProcess.STATE_CHECK) {
                                    EventBus.getDefault().post(
                                        MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                                }
                            }
                            else {
                                postNewVersionToView(info);
                                LogUtil.log("maybe md5,url or forceupgrade is different!");
                                if (lastTask.getUpgradeInfo().isJustFroceNotEqual(info)) {
                                    LogUtil.log("isJustFroceNotEqual!");
                                    updateLastTask(lastTask, info);
                                    EventBus.getDefault().post(
                                        MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                                }
                                else {
                                    LogUtil.log("MD5 or url is diffrent!");
                                    overCurrTask(lastTask);
                                    saveNewTask(info);
                                    EventBus.getDefault().post(
                                        MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                                }
                            }
                        }
                        else {
                            LogUtil.log("CheckProcess check pkg is new, over curr task, do new task! ");
                            overCurrTask(lastTask);
                            saveNewTask(info);
                            postNewVersionToView(info);
                            EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                        }
                    }
                }
                else {
                    postNoVersionToView(CommonUtil.MSG_NO_VERSION);
                }
            }
        };
        cancelable = RequestUtil.sendRequest(RequestUtil.getCheckUpgradeUrl(), composeJson(), callback);
    }

    @Override
    public void stopProcess() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    private void overCurrTask(UpgradeTask lastTask) {
        lastTask.setState(StateProcess.STATE_COMPLETE);
        UpgradeApp.getInstance().getTaskManager().updateObj(lastTask);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_STOP_CURR_TASK, 0));
    }

    private void saveNewTask(UpgradeInfo info) {
        getTask().setUpgradeInfo(info);
        getTask().setTaskstarttime(System.currentTimeMillis());
        getTask().setState(STATE_DOWNLOAD);
        getTask().setPresoft(CommonUtil.getSoftVersion());
        UpgradeApp.getInstance().getTaskManager().saveObj(getTask());
    }

    private void updateLastTask(UpgradeTask lastTask, UpgradeInfo info) {
        lastTask.setUpgradeInfo(info);
        lastTask.setTaskstarttime(System.currentTimeMillis());
        lastTask.setPresoft(CommonUtil.getSoftVersion());
        UpgradeApp.getInstance().getTaskManager().updateObj(lastTask);
    }

    private JSONObject composeJson() {
        LogUtil.log("composeJson");
        JSONObject json = null;
        DeviceInfo deviceInfo = UpgradeApp.getInstance().getTaskManager().obtainDeviceInfo();
        LogUtil.log("composeJson deviceInfo : " + deviceInfo);
        if (deviceInfo != null) {
            json = baseJsonFromService(deviceInfo);
        }
        else {
            json = baseJson();
        }
        return json;
    }

    private JSONObject baseJson() {
        LogUtil.log("baseJson");
        JSONObject json = new JSONObject();
        try {
            json.put("did", CommonUtil.getDeviceNo());
            json.put("hardversion", CommonUtil.getHardVersion());
            json.put("version", CommonUtil.getSoftVersion());
            json.put("devicetype", CommonUtil.getDeviceType());
            json.put("vendor", CommonUtil.getVendor());
            String sn = CommonUtil.getSnno();
            json.put("snno", sn);
            if (getTask().isManual()) {
                json.put("manual", "1");
            }
            else {
                json.put("manual", "0");
            }
            json.put("customerid", CommonUtil.getCustomerid(sn));
            json.put("isCheckDeviceInfo", "0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject baseJsonFromService(DeviceInfo deviceInfo) {
        LogUtil.log("baseJsonFromService");
        JSONObject json = new JSONObject();
        try {
            json.put("did", CommonUtil.getDeviceNo());
            json.put("hardversion", CommonUtil.getHardVersion());
            json.put("version", CommonUtil.getSoftVersion());
            json.put("devicetype", deviceInfo.getDeviceTypeName());
            json.put("vendor", deviceInfo.getVendorId());
            String sn = deviceInfo.getSnNo();
            json.put("snno", sn);
            if (getTask().isManual()) {
                json.put("manual", "1");
            }
            else {
                json.put("manual", "0");
            }
            json.put("customerid", deviceInfo.getCustomerId());
            json.put("isCheckDeviceInfo", "1");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private void postNoVersionToView(int faildCase) {
        LogUtil.log("postBroadcastToView");

        MessageEvent event = MessageEvent.createMsg(MessageEvent.MSG_CHECK_NEW_VERSION, 0);
        event.setParam(faildCase);
        EventBus.getDefault().post(event);

        try {
            sendBroadcastToView(null);
            sendOldBroadcastToView(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postNewVersionToView(UpgradeInfo info) {
        LogUtil.log("postBroadcastToView");

        MessageEvent event = MessageEvent.createMsg(MessageEvent.MSG_CHECK_NEW_VERSION, 0);
        event.setParam(CommonUtil.MSG_FOUND_VERSION);
        EventBus.getDefault().post(event);

        if (null == info) {
            return;
        }

        try {
            sendBroadcastToView(info);
            sendOldBroadcastToView(info);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendBroadcastToView(UpgradeInfo info) {
        Intent in = new Intent(IUpgradeBracast.ACTION_CHECKUPDATE);
        Locale locale = UpgradeApp.getInstance().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String description = null;
        if (info == null) {
            in.putExtra(CommonUtil.ISNEWVERSION, 0);
            UpgradeApp.getInstance().sendBroadcast(in);
        }
        else {

            if (language.endsWith("zh")) {
                description = info.getPkgcndesc();
            }
            else {
                description = info.getPkgendesc();
            }
            in.putExtra(CommonUtil.ISNEWVERSION, 1);
            in.putExtra(CommonUtil.ISFORCEUPGRADE, info.getForceupgrade());
            in.putExtra(CommonUtil.DESCRIPTION, description);
            in.putExtra(CommonUtil.PKGVERSION, info.getPkgversion());
            UpgradeApp.getInstance().sendBroadcast(in);
        }
    }

    private void sendOldBroadcastToView(UpgradeInfo info) {
        Intent intent = new Intent("android.action.ics.mipt.ota.checkupdate");
        if (info == null) {
            intent.putExtra("new", 0);
        }
        else {
            intent.putExtra("new", 1);
        }
        UpgradeApp.getInstance().sendBroadcast(intent);
    }
}
