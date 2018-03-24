package com.skyworthdigital.upgrade.util;

import java.io.FileInputStream;
import java.util.Properties;

import android.util.Log;

public class Config {
    private static final String TEST_FILE = "/data/upgradetest.properties";
    private Properties properties = new Properties();

    /**
     * load test file
     */
    public Config() {
        try {
            properties.load(new FileInputStream(TEST_FILE));
        }
        catch (Exception e) {
            LogUtil.log("test file is not exist : " + TEST_FILE);
        }
    }

    /**
     * whether test.
     * @return
     */
    public boolean isTest() {
        String value = properties.getProperty("test");
        boolean result = Boolean.parseBoolean(value);
        Log.d("SkyWeChatClient", "Config test : " + result);
        return result;
    }

    public String getOtaServer() {
        String value = properties.getProperty("otaserver");
        return value;
    }

    public String getDeviceServer() {
        String value = properties.getProperty("deviceserver");
        return value;
    }
}
