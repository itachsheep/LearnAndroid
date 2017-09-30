package com.skyworthdigital.upgrade.state.process;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.db.DeviceInfo;
import com.skyworthdigital.upgrade.port.CheckUpgradeResult;
import com.skyworthdigital.upgrade.port.UpgradeInfo;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;
import com.skyworthdigital.upgrade.utils.RequestUtil;
import com.skyworthdigital.upgrade.utils.SkyRootUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;

import de.greenrobot.event.EventBus;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class CheckProcess extends StateProcess{
    private Callback.Cancelable cancelable = null;

    public CheckProcess() {
        super(null);
    }

    @Override
    public void run() {
        Callback.CommonCallback<String> callBack = new Callback.CommonCallback<String>() {


            @Override
            public void onError(Throwable throwable, boolean b) {
                throwable.printStackTrace();
                LogUtil.i("CheckProcess.onError : " + throwable.toString());
                CheckProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                postNoVersionToView(IConstants.MSG_NETWORK_INVALID);
            }

            @Override
            public void onCancelled(CancelledException e) {
                LogUtil.i("CheckProcess.onCancelled");
            }

            @Override
            public void onFinished() {
                LogUtil.i("CheckProcess.onFinished");
            }

            @Override
            public void onSuccess(String result) {
                LogUtil.i("CheckProcess.onSuccess : " + result);
                Gson gson = new Gson();
                CheckUpgradeResult upgradeResult = gson.fromJson(result,CheckUpgradeResult.class);
                LogUtil.i("CheckProcess.onSuccess : " + upgradeResult);

                CheckProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);

                if(upgradeResult.getCode() == 0){
                    UpgradeInfo info = upgradeResult.getData().getUpgradeinfo();
                    UpgradeTask lastTask = UpgradeApp.getInstance().getTaskManager().obtainLastTask();


                    // TODO: 2017/9/25  if not fullpck && isroot --> return????
                    if(info.getPkgtype() != IConstants.FULL_PACKAGE ){
                        boolean isRoot = SkyRootUtils.isBoxRoot();
                        if(isRoot){
                            LogUtil.i("CheckProcess box is root");
                            postNoVersionToView(IConstants.MSG_SYSTEM_HAS_ROOT);
                            return;
                        }
                    }

                    dealWithUpgradeResult(info, lastTask);

                }else {
                    postNoVersionToView(IConstants.MSG_NO_VERSION);
                }
            }
        };
        cancelable = RequestUtil.sendRequest(RequestUtil.getCheckUpgradeUrl(), composeJson(), callBack);
    }

    private void dealWithUpgradeResult(UpgradeInfo info, UpgradeTask lastTask) {
        if (lastTask == null) {
            LogUtil.i("CheckProcess.dealWithUpgradeResult check first pkg, save and do it! ");
            saveNewTask(info);
            postNewVersionToView(info);
            EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
        }else {
            if (lastTask.getUpgradeInfo().isIDEqual(info)) {
                if (lastTask.getUpgradeInfo().equals(info)) {
                    LogUtil.i("CheckProcess.dealWithUpgradeResult check pkg is the same, do the curr task!"
                            +" state: "+lastTask.getState());

                    if (lastTask.getState() == CheckProcess.STATE_COMPLETE) {
                        saveNewTask(info);
                    }
                    postNewVersionToView(info);

                    if (lastTask.getState() != CheckProcess.STATE_CHECK) {
                        EventBus.getDefault().post(
                                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                    }
                } else {
                    postNewVersionToView(info);
                    LogUtil.i("CheckProcess.dealWithUpgradeResult maybe md5,url or forceupgrade is different!");
                    if (lastTask.getUpgradeInfo().isJustFroceNotEqual(info)) {
                        LogUtil.i("CheckProcess.dealWithUpgradeResult isJustFroceNotEqual!");
                        updateLastTask(lastTask, info);
                        EventBus.getDefault().post(
                                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                    } else {
                        LogUtil.i("CheckProcess.dealWithUpgradeResult MD5 or url is diffrent!");
                        overCurrTask(lastTask);
                        saveNewTask(info);
                        EventBus.getDefault().post(
                                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
                    }
                }
            } else {
                LogUtil.i("CheckProcess.dealWithUpgradeResult check pkg is new, over curr task, do new task! ");
                overCurrTask(lastTask);
                saveNewTask(info);
                postNewVersionToView(info);
                EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, 0));
            }
        }
    }

    private void overCurrTask(UpgradeTask lastTask) {
        lastTask.setState(StateProcess.STATE_COMPLETE);
        UpgradeApp.getInstance().getTaskManager().updateObj(lastTask);
        EventBus.getDefault().post(MessageEvent.createMsg(MessageEvent.EVENT_STOP_CURR_TASK, 0));
    }

    private void updateLastTask(UpgradeTask lastTask, UpgradeInfo info) {
        lastTask.setUpgradeInfo(info);
        lastTask.setTaskstarttime(System.currentTimeMillis());
        lastTask.setPresoft(CommonUtil.getSoftVersion());
        UpgradeApp.getInstance().getTaskManager().updateObj(lastTask);
    }



    private void saveNewTask(UpgradeInfo info) {
        getTask().setUpgradeInfo(info);
        getTask().setTaskstarttime(System.currentTimeMillis());
        getTask().setState(STATE_DOWNLOAD);
        getTask().setPresoft(CommonUtil.getSoftVersion());
        UpgradeApp.getInstance().getTaskManager().saveObj(getTask());
    }

    @Override
    public void stopProcess() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    private JSONObject composeJson() {
        LogUtil.i("CheckProcess.composeJson");
        JSONObject json = null;
        DeviceInfo deviceInfo = UpgradeApp.getInstance().getTaskManager().obtainDeviceInfo();
        LogUtil.i("CheckProcess.composeJson deviceInfo : " + deviceInfo);
        if (deviceInfo != null) {
            json = baseJsonFromService(deviceInfo);
        }
        else {
            json = baseJson();
        }
        return json;
    }

    private JSONObject baseJson() {
        LogUtil.i("CheckProcess.baseJson");
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

            json.put("isCheckDeviceInfo", IConstants.IS_TEST_MODE? "1":"0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    private JSONObject baseJsonFromService(DeviceInfo deviceInfo) {
        LogUtil.i("CheckProcess.baseJsonFromService");
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
        LogUtil.i("CheckProcess.postBroadcastToView faildCase:"+faildCase);

        MessageEvent event = MessageEvent.createMsg(MessageEvent.MSG_CHECK_NEW_VERSION, 0);
        event.setParam(faildCase);
        EventBus.getDefault().post(event);
        // TODO: 2017/9/25 remove this function
        /*try {
            sendBroadcastToView(null);
            sendOldBroadcastToView(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void postNewVersionToView(UpgradeInfo info) {
        LogUtil.i("CheckProcess.postBroadcastToView info..");

        MessageEvent event = MessageEvent.createMsg(MessageEvent.MSG_CHECK_NEW_VERSION, 0);
        event.setParam(IConstants.MSG_FOUND_VERSION);
        EventBus.getDefault().post(event);

        if (null == info) {
            return;
        }
        // TODO: 2017/9/25 remove this function
        /*try {
            sendBroadcastToView(info);
            sendOldBroadcastToView(info);
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
