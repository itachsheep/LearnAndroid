package com.skyworthdigital.upgrade.upload;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AreaObtainer {
    public static final String URL_QUERY_ALIBABA = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
    public static final String URL_QUERY_PCONLINE = "http://whois.pconline.com.cn/ipJson.jsp?json=true&level=3";
    public static final String URL_QUERY_SKYWORTH = "http://cfg.skyworthbox.com/adsys/api/getIpInfo";
    public static final String KEY_DOMAIN_ADVERT = "ro.stb.adv.url";
    public static final String KEY_DEVICEID = "deviceId";
    public static final String KEY_TIME = "t";
    public static final String KEY_SNNO = "snNo";
    public static final String KEY_CHECKSUM = "checksum";
    private static String localCity = "";
    private static String connectPublicIP = "";
    private static String localPro = "";
    private static String localCounty = "";
    
    
    public static String getLocalCity() {
        return localCity;
    }

    public static String getConnectPublicIP() {
        return connectPublicIP;
    }

    public static String getLocalPro() {
        return localPro;
    }

    public static String getLocalCounty() {
        return localCounty;
    }

    public static void obtainerArea() {
        queryAreaFromAlibaba();
        queryAreaFromSkyworth();
        queryAreaFromPconline();
    }
    
    private static void queryAreaFromAlibaba() {
        // http://ip-api.com/json
        LogUtil.i("DeviceInfo queryArea from Alibaba");
        RequestParams params = new RequestParams(URL_QUERY_ALIBABA);
        params.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
        x.http().get(params, obtainAreaCallback);
    }
    
    @SuppressLint("DefaultLocale")
    private static void queryAreaFromSkyworth() {
        // http://ip-api.com/json
        LogUtil.i("DeviceInfo queryArea from skyworth");
        String url = getSkyworthUrl();
        RequestParams params = new RequestParams(url);
        String deviceid = CommonUtil.getDeviceNo();
        long time = System.currentTimeMillis();
        String snno = CommonUtil.getSnno();
        params.addParameter(KEY_DEVICEID, deviceid);
        params.addParameter(KEY_TIME, time);
        params.addParameter(KEY_SNNO, snno);
        String checkSource = deviceid + "&" + time + "&" + snno;
        //统一小写md5
        String md5 = CommonUtil.getMd5String(checkSource)
                            .toLowerCase();
        String checksum = md5.substring(md5.length() / 2);
        params.addParameter(KEY_CHECKSUM, checksum);
        x.http().get(params, obtainAreaCallback);
    }
    
    private static void queryAreaFromPconline() {
        // http://ip-api.com/json
        LogUtil.i("DeviceInfo queryArea from pconline");
        RequestParams params = new RequestParams(URL_QUERY_PCONLINE);
        params.setCharset("GBK");
        x.http().get(params, obtainAreaCallback2);
    }
    
    private static CommonCallback<String> obtainAreaCallback = new CommonCallback<String>() {

        @Override
        public void onCancelled(CancelledException arg0) {
            LogUtil.i("DeviceInfo obtainArea onCancelled");
        }

        @Override
        public void onError(Throwable arg0, boolean arg1) {
            LogUtil.i("DeviceInfo obtainArea onError : " + arg0.getMessage());
        }

        @Override
        public void onFinished() {
            LogUtil.i("DeviceInfo obtainArea onFinished");
        }

        @Override
        public void onSuccess(String str) {
            LogUtil.i("DeviceInfo obtainArea onSuccess str : " + str);
            Gson sgon = new Gson();
            AreaInfoTaobaoResult info = sgon.fromJson(str, AreaInfoTaobaoResult.class);
            LogUtil.i("DeviceInfo obtainArea info : " + info);
            
            if(info.getCode() == 0) {
                updateAreaPublicIP(info.getData().getIp());
                updateAreaLocalCity(info.getData().getCity());
                updateAreaLocalPro(info.getData().getRegion());
                updateAreaLocalCounty(info.getData().getCounty());
            }
        }
    };
    
    private static CommonCallback<String> obtainAreaCallback2 = new CommonCallback<String>() {

        @Override
        public void onCancelled(CancelledException arg0) {
            LogUtil.i("DeviceInfo obtainArea onCancelled");
        }

        @Override
        public void onError(Throwable arg0, boolean arg1) {
            LogUtil.i("DeviceInfo obtainArea onError : " + arg0.getMessage());
        }

        @Override
        public void onFinished() {
            LogUtil.i("DeviceInfo obtainArea onFinished");
        }

        @Override
        public void onSuccess(String str) {
            LogUtil.i("DeviceInfo obtainArea onSuccess str : " + str);
            Gson sgon = new Gson();
            AreaPconline info = sgon.fromJson(str, AreaPconline.class);
            LogUtil.i("DeviceInfo obtainArea info : " + info);
            
            if(info != null) {
                updateAreaPublicIP(info.getIp());
                updateAreaLocalCity(info.getCity());
                updateAreaLocalPro(info.getPro());
                updateAreaLocalCounty(info.getRegion());
            }
        }
    };
    
    private static void updateAreaPublicIP(String ip) {
        if(TextUtils.isEmpty(connectPublicIP)) {
            connectPublicIP = ip;
        }
    }
    
    private static void updateAreaLocalCity(String city) {
        if(TextUtils.isEmpty(localCity)) {
            localCity = city;
        }
    }
    
    private static void updateAreaLocalPro(String province) {
        if(TextUtils.isEmpty(localPro)) {
            localPro = province;
        }
    }
    
    private static void updateAreaLocalCounty(String county) {
        if(TextUtils.isEmpty(localCounty)) {
            localCounty = county;
        }
    }

    public static String getSkyworthUrl() {
        String server = null;
        String advertServer = CommonUtil.getSystemProperties(KEY_DOMAIN_ADVERT);
        if (!TextUtils.isEmpty(advertServer)) {
            server = "http://" + advertServer + "/adsys/api/getIpInfo";
            return server;
        }
        return URL_QUERY_SKYWORTH;
    }
}
