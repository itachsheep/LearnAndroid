package com.skyworthdigital.upgrade.upload;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Handler;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.DeviceInfo;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;
import com.skyworthdigital.upgrade.utils.RequestUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class DeviceInfoController {

    private Callback.CommonCallback<String> uploadDeviceCallback = new Callback.CommonCallback<String>() {

        @Override
        public void onCancelled(CancelledException arg0) {
            LogUtil.i("DeviceInfo uploadDeviceInfo onCancelled");
        }

        @Override
        public void onError(Throwable arg0, boolean arg1) {
            LogUtil.i("DeviceInfo uploadDeviceInfo onError : " + arg0.getMessage());
        }

        @Override
        public void onFinished() {
            LogUtil.i("DeviceInfo uploadDeviceInfo onFinished");
        }

        @Override
        public void onSuccess(String str) {
            LogUtil.i("DeviceInfo uploadDeviceInfo onSuccess str : " + str);
            Gson sgon = new Gson();
            DeviceInfoResult info = sgon.fromJson(str, DeviceInfoResult.class);
            LogUtil.i("DeviceInfo uploadDeviceInfo onSuccess info : " + info);
            DeviceInfo deviceInfo = UpgradeApp.getInstance().getTaskManager().obtainDeviceInfo();
            if (deviceInfo != null && deviceInfo.getId() == info.getData().getId()) {
                UpgradeApp.getInstance().getTaskManager().updateObj(info.getData());
            }
            else {
                UpgradeApp.getInstance().getTaskManager().saveObj(info.getData());
            }
            // TODO: 2017/9/28 将服务器同步的信息写入contentProvider,且写入flash中
            //addDeviceInfoToContentProvide(info.getData());
            //CommonUtil.writeSN2Flash(info.getData().getSnNo());
        }
    };

    List<ContentValues> dataList = new LinkedList<ContentValues>();
    private void addDeviceInfo(String key,String value) {

        ContentValues cv = new ContentValues();
        cv.put(IConstants.KEY, key);
        cv.put(IConstants.VALUE, value);

        LogUtil.i(key + "\n");
        LogUtil.i(value + "\n");
        dataList.add(cv);

    }

    private void addDeviceInfoToContentProvide(DeviceInfo deviceinfo) {
        addDeviceInfo("area", deviceinfo.getArea());
        addDeviceInfo("customerId", deviceinfo.getCustomerId());
        addDeviceInfo("customerName", deviceinfo.getCustomerName());
        addDeviceInfo("customerSnNo", deviceinfo.getCustomerSnNo());
        addDeviceInfo("deviceNo", deviceinfo.getDeviceNo());
        addDeviceInfo("deviceTypeId", deviceinfo.getDeviceTypeId());
        addDeviceInfo("deviceTypeName", deviceinfo.getDeviceTypeName());
        addDeviceInfo("hardversion", deviceinfo.getHardVersion());
        addDeviceInfo("lastConnectIp", deviceinfo.getLastConnectIp());
        addDeviceInfo("network", deviceinfo.getNetwork());
        addDeviceInfo("snNo", deviceinfo.getSnNo());
        addDeviceInfo("softversion", deviceinfo.getSoftVersion());
        addDeviceInfo("vendorId", deviceinfo.getVendorId());
        addDeviceInfo("wiredMac", deviceinfo.getWiredMac());
        addDeviceInfo("wirelessMac", deviceinfo.getWirelessMac());
        addDeviceInfo("deviceTypeNameDesc", deviceinfo.getDeviceTypeNameDesc());

        if ((dataList != null) && dataList.size() > 0) {
            // clear old device info
            ContentResolver cr = UpgradeApp.getInstance().getContentResolver();
            int count = cr.delete(IConstants.CONTENT_URI, null, null);
            LogUtil.i("Delete old device info: " + count);

            // insert new device info
            ContentValues[] values = new ContentValues[dataList.size()];
            values = dataList.toArray(values);
            int newCnt = cr.bulkInsert(IConstants.CONTENT_URI, values);
            LogUtil.i("Insert new device info: " + newCnt);
        }
    }


    public void uploadDeviceInfo() {
        LogUtil.i("DeviceInfo uploadDeviceInfo");
        AreaObtainer.obtainerArea();
        new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

            public void run() {
                RequestUtil.sendRequest(RequestUtil.getUploadDeviceUrl(),getDeviceJson(),uploadDeviceCallback);
            }

//        }, IConstants.SECONDS_10);
        }, IConstants.SECONDS_5);
    }

    private JSONObject getDeviceJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("deviceNo", CommonUtil.getDeviceNo());
            json.put("hardversion", CommonUtil.getHardVersion());
            json.put("softversion", CommonUtil.getSoftVersion());
            String sn = CommonUtil.getSnno();
            json.put("snNo", sn);
            json.put("deviceTypeId", CommonUtil.getDeviceTypeId(sn));
            json.put("factoryId", CommonUtil.getFactoryId(sn));
            json.put("customerId", CommonUtil.getCustomerid(sn));
            json.put("wirelessMac", CommonUtil.getWirelessMacAddress(UpgradeApp.getInstance()));
            json.put("wiredMac", CommonUtil.getWiredMacAddress(UpgradeApp.getInstance()));
            json.put("network", CommonUtil.getNetWorkWay());
            json.put("connectIp", AreaObtainer.getConnectPublicIP());
            json.put("area", AreaObtainer.getLocalCity());
            json.put("province", AreaObtainer.getLocalPro());
            json.put("county", AreaObtainer.getLocalCounty());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
