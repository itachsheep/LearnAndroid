package com.skyworthdigital.upgrade.util;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.skyworthdigital.upgrade.UpgradeApp;

import android.text.TextUtils;
import android.util.Base64;

public class RequestUtil {
    public static final String KEY_SERVER_OTA = "ro.stb.ota.url";
    public static final String KEY_SERVER_DEVICE = "ro.stb.ott.url";
    // http://192.168.114.118:8080/ota/checkupgrade?
    private static final String SERVER_OTA = "https://ota.skyworthbox.com";
    private static final String SERVER_DEVICE = "https://ota.skyworthbox.com";

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

    public static String getOtaService() {
        return getOtaServer() + "/otav2";
    }

    public static String getDeviceService() {
        return getDeviceServer() + "/devicev2";
    }

    public static String getCheckUpgradeUrl() {
        return getOtaService() + "/checkupgrade?";
    }

    public static String getUploadDownloadUrl() {
        return getOtaService() + "/uploaddownload?";
    }

    public static String getUploadUpgradeUrl() {
        return getOtaService() + "/uploadupgrade?";
    }

    public static String getUploadDeviceUrl() {
        return getDeviceService() + "/publicInterface/reportDeviceInfo?";
    }

    public static Callback.Cancelable sendRequest(String url, JSONObject json, CommonCallback<String> callback) {
        String param = Base64.encodeToString(json.toString().getBytes(), Base64.NO_WRAP);
        String key = CryptUtils.hmacSHA1Encrypt(param, CryptUtils.PRIVATE_KEY);

        LogUtil.log("url : " + url);
        LogUtil.log("key : " + key);
        LogUtil.log("param : " + param);
        LogUtil.log("json : " + json.toString());

        RequestParams params = new RequestParams(url);
        params.addParameter("key", key);
        params.addParameter("param", param);
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }
}
