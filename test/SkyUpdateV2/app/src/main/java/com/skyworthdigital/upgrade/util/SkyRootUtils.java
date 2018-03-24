package com.skyworthdigital.upgrade.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SkyRootUtils {

    private static final String SU_BIN_PATH = "/system/bin/su";
    private static final String SU_XBIN_PATH = "/system/xbin/su";
    private static final String ETC_PATH = "/system/etc/";
    private static final String BIN_PATH = "/system/bin/";
    private static final String XBIN_PATH = "/system/xbin/";
    private static final String SU_AUTHORITY = "-rwsr-x---";
    private static final String FORBID_PERMISION = "ia";
    private static final int FORBID_PERMISION_STR_LENTH = 7;
    private static final int AUTHORITY_STR_LENTH = 10;
    private static final int COMPARE_SDK_VERSION = 4;

    public static boolean isBoxRoot() {
        try {
            if (isBulidInSu()) {
                if (isSuPermisonChanged(SU_BIN_PATH) || isSuPermisonChanged(SU_XBIN_PATH)) {
                    return true;
                }
            }
            else {
                if (isSuExists()) {
                    return true;
                }
                else {
                    if (isAnyFileNotModify()) {
                        return true;
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isAnyFileNotModify() {

        if (checkFilesPermison(XBIN_PATH)) {
            return true;
        }
        else if (checkFilesPermison(BIN_PATH)) {
            return true;
        }
        else if (checkFilesPermison(ETC_PATH)) {
            return true;
        }
        return false;
    }

    private static boolean isBulidInSu() {
        String currentVersion = getSDKVersion();
        if (currentVersion != null && currentVersion.length() > 0) {
            String versionStr = currentVersion.substring(0, 1);
            LogUtil.log("versionStr : " + versionStr);
            if (versionStr != null) {
                int versionNum = Integer.parseInt(versionStr);
                LogUtil.log("versionNum : " + versionNum);
                if (versionNum > COMPARE_SDK_VERSION) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String getSDKVersion() {

        String buildVersion = "";
        buildVersion = android.os.Build.VERSION.RELEASE;
        LogUtil.log("current build version : " + buildVersion);
        return buildVersion;
    }

    private static boolean isSuExists() {

        if (fileIsExists(SU_BIN_PATH)) {
            LogUtil.log(SU_BIN_PATH + " is exists!");
            return true;
        }
        if (fileIsExists(SU_XBIN_PATH)) {
            LogUtil.log(SU_XBIN_PATH + " is exists!");
            return true;
        }
        return false;
    }

    private static boolean fileIsExists(String filePath) {

        if (filePath == null) {
            return false;
        }

        File suFile = new File(filePath);
        if (suFile != null) {
            if (suFile.exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSuPermisonChanged(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            LogUtil.log(str);
            if (str != null && str.length() >= AUTHORITY_STR_LENTH) {
                String authStr = str.substring(0, AUTHORITY_STR_LENTH);
                LogUtil.log(authStr);
                if (authStr != null) {
                    if (!authStr.equals(SU_AUTHORITY)) {
                        return true;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

    private static boolean checkFilesPermison(String dir) {
        if (dir == null) {
            return false;
        }
        File fileDir = new File(dir);
        if (fileDir == null || !fileDir.isDirectory()) {
            return false;
        }
        File[] fs = fileDir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isFile()) {
                String str = fs[i].getAbsolutePath();
                LogUtil.log("checkFilesPermison : " + str);
                if (isforbidWriting(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isforbidWriting(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("lsattr " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            LogUtil.log("isforbidWriting : " + str);
            if (str != null && str.length() >= FORBID_PERMISION_STR_LENTH) {
                String permisionStr = str.substring(FORBID_PERMISION_STR_LENTH - 2, FORBID_PERMISION_STR_LENTH);
                LogUtil.log("isforbidWriting : " + permisionStr);
                if (permisionStr != null) {
                    if (permisionStr.equals(FORBID_PERMISION)) {
                        return true;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }
}
