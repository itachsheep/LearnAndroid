package com.skyworthdigital.upgrade.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class Config {
    private static final String TEST_FILE = "/data/upgradetest.properties";
    private Properties properties = new Properties();

    public Config() {
        try {
            properties.load(new FileInputStream(TEST_FILE));
        }
        catch (Exception e) {
            LogUtil.i("test file is not exist : " + TEST_FILE);
        }
    }

    public boolean isTest() {
        String value = properties.getProperty("test");
        boolean result = Boolean.parseBoolean(value);
        LogUtil.i( "Config test : " + result);
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
