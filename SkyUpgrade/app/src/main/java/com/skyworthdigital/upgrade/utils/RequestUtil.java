package com.skyworthdigital.upgrade.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.UpgradeApp;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class RequestUtil {
    public static final String KEY_SERVER_OTA = "ro.stb.ota.url";

    // http://192.168.114.118:8080/ota/checkupgrade?
    private static final String SERVER_OTA = IConstants.IS_TEST_MODE ? "http://192.168.0.76":"https://ota.skyworthbox.com";
    public static final String KEY_SERVER_DEVICE = "ro.stb.ott.url";
    private static final String SERVER_DEVICE = IConstants.IS_TEST_MODE ? "http://192.168.0.76":"https://ota.skyworthbox.com";

    public static Callback.Cancelable sendRequest(String url, JSONObject json, CommonCallback<String> callback) {
        String param = Base64.encodeToString(json.toString().getBytes(), Base64.NO_WRAP);
        String key = CryptUtils.hmacSHA1Encrypt(param, CryptUtils.PRIVATE_KEY);

//        if(IConstants.IS_TEST_MODE){
//            key = "5e7840efd38b56d2676a3838a302f2613e1fd5c2";
//            param = "eyJjdXN0b21lcmlkIjoiMzAwMDEiLCJkZXZpY2V0eXBlIjoiNzA4NSIsImRpZCI6IjM4RkFDQTc4NEU0RDE3MjNaQnR5OCIsImhhcmR2ZXJzaW9uIjoiMSIsImlzQ2hlY2tEZXZpY2VJbmZvIjoiMSIsIm1hbnVhbCI6IjAiLCJzbm5vIjoiMDM3MDg1MzAwMDExNjIwMDAyODU5OSIsInZlbmRvciI6IjAzIiwidmVyc2lvbiI6IjEifQ==";
//
//        }


        LogUtil.i("url : " + url);
        LogUtil.i("key : " + key);
        LogUtil.i("param : " + param);
        LogUtil.i("json : " + json.toString());

        RequestParams params = new RequestParams(url);
        params.addParameter("key", key);
        params.addParameter("param", param);
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    public static String getCheckUpgradeUrl() {
        return getOtaService() + "/checkupgrade?";
    }
    public static String getOtaService() {
        return getOtaServer() + "/otav2";
    }
    public static String getOtaServer() {
        String server = CommonUtil.getSystemProperties(KEY_SERVER_OTA);
        if (UpgradeApp.getInstance().getConfig().isTest()) {
            server = UpgradeApp.getInstance().getConfig().getOtaServer();
        }
        if (TextUtils.isEmpty(server)) {
            server = SERVER_OTA;
        }
        else {
            if (!server.startsWith("http")) {
                server = "http://" + server;
            }
        }
        return server;
    }

    public static String getUploadDownloadUrl() {
        return getOtaService() + "/uploaddownload?";
    }
    public static String getUploadDeviceUrl() {
        return getDeviceService() + "/publicInterface/reportDeviceInfo?";
    }
    public static String getDeviceService() {
        return getDeviceServer() + "/devicev2";
    }
    public static String getDeviceServer() {
        String server = CommonUtil.getSystemProperties(KEY_SERVER_DEVICE);
        if (UpgradeApp.getInstance().getConfig().isTest()) {
            server = UpgradeApp.getInstance().getConfig().getDeviceServer();
        }
        if (TextUtils.isEmpty(server)) {
            server = SERVER_DEVICE;
        }
        else {
            if (!server.startsWith("http")) {
                server = "http://" + server;
            }
        }
        return server;
    }


    public static String getUploadUpgradeUrl() {
        return getOtaService() + "/uploadupgrade?";
    }
}
