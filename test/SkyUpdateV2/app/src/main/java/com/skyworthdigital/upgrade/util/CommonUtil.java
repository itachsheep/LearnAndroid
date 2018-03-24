package com.skyworthdigital.upgrade.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.SyncFailedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import java.util.Locale;

import com.skyworth.sys.util.SkyworthManager;
import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.model.UpgradeTask;

// import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

public class CommonUtil {

    public static final String PROPERTY_DEFAULT_DIR = "ro.stb.ota.dir";
    public static final String UPDATEZIP = "update.zip";
    public static final long DAY_LONG = 8 * 60 * 60 * 1000;
    public static final String timeVerify = "2016-01-14 19:13:00";
    public static final int UPLOAD_UPGRADE_RESULT_TIMES = 3;
    public static final int DOWLOAD_TIMES = 6;
    public static final String ACTION_AUTO_CHECK = "skyworth.intent.action.OTA_AUTO_CHECK";

    public static final int ERR_CODE_NO_SPACE = 1;
    public static final int ERR_CODE_NETWORK = 2;
    public static final int ERR_CODE_MD5_ERR = 3;
    public static final int MSG_NETWORK_INVALID = 4;
    public static final int MSG_NO_VERSION = 5;
    public static final int MSG_FOUND_VERSION = 6;
    public static final int MSG_SYSTEM_HAS_ROOT = 7;

    public static String ISNEWVERSION = "isnewversion";
    public static String ISFORCEUPGRADE = "isforceupgrade";
    public static String ISUPGRADERESULT = "isupgraderesult";
    public static String DESCRIPTION = "description";
    public static String PKGVERSION = "pkgversion";
    public static String ERRCODE = "errcode";
    public static String PROGRESS = "progress";

    public static final int FULL_PACKAGE = 1;
    public static final int INCREASE_PACKAGE = 2;
    public static final int PATCH_PACKAGE = 3;

    private static boolean isRecoveryMode = false;
    private static boolean FIRST_RECEIVE_CONNECT_CHANGE = true;
    private static boolean NETWORK_CONNECT_STATE = false;
    private static boolean HAS_RECEIVED_BOOT_COMPLETED = false;

    public static final String CONTENT_PATH = "deviceinfo";
    public static final String AUTHORITY = "com.skyworthdigital.upgrade";


    private static boolean isUploadDeviceInfo = false;

    public static boolean getUploadDeviceInfoState() {
        return isUploadDeviceInfo;
    }

    public static void setUploadDeviceInfoState(boolean state) {
        isUploadDeviceInfo = state;
    }
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.skyworthdigital.ott.deviceinfo";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.skyworthdigital.ott.deviceinfo";

    /**
     * The title of the function entry
     */
    public static final String KEY = "key";

    /**
     * The value of the function entry
     */
    public static final String VALUE = "value";

    public static String getDeviceNo() {
        // persist.sys.hwconfig.stb_id
        return getSystemProperties("persist.sys.hwconfig.stb_id");
    }

    public static String getHardVersion() {
        // persist.sys.hwconfig.hw_ver
        return getSystemProperties("persist.sys.hwconfig.hw_ver");
    }

    public static String getSoftVersion() {
        // persist.sys.hwconfig.soft_ver
        return getSystemProperties("persist.sys.hwconfig.soft_ver");
    }

    public static String getDeviceType() {
        // ro.product.model
        return getSystemProperties("ro.product.model");
    }

    public static String getDeviceTypeId(String sn) {
        if (TextUtils.isEmpty(sn)) {
            return null;
        }
        String devicetypeid = sn.substring(2, 6);
        return devicetypeid;
    }

    public static String getFactoryId(String sn) {
        if (TextUtils.isEmpty(sn)) {
            return null;
        }
        String factoryid = sn.substring(0, 2);
        return factoryid;
    }

    public static String getVendor() {
        // ro.product.manufacturer
        return getSystemProperties("ro.product.manufacturer");
    }

    public static String getSnno() {
        // persist.sys.hwconfig.seq_id
        return getSystemProperties("persist.sys.hwconfig.seq_id");
    }

    public static String getCustomerid(String sn) {
        if (TextUtils.isEmpty(sn)) {
            return null;
        }
        String customerid = sn.substring(6, 11);
        return customerid;
    }

    public static String getWirelessMacAddress(Context context) {

        String wifiMac = null;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
            wifiMac = info.getMacAddress();
        }

        if (wifiMac == null) {
            return "";
        }
        else {
            return wifiMac;
        }

    }

    public static String getWiredMacAddress(Context context) {

        String mac = "";
        try {
            Enumeration<NetworkInterface> localEnumeration = NetworkInterface.getNetworkInterfaces();
            while (localEnumeration.hasMoreElements()) {
                NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration.nextElement();
                String interfaceName = localNetworkInterface.getDisplayName();
                LogUtil.log("getWiredMacAddress interfaceName : " + interfaceName);

                if (interfaceName == null) {
                    continue;
                }

                if (interfaceName.startsWith("eth")) {
                    mac = convertToMac(localNetworkInterface.getHardwareAddress());
                    if (mac != null && mac.startsWith("0:")) {
                        mac = "0" + mac;
                    }
                    break;
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        LogUtil.log("getWiredMacAddress mac : " + mac);
        return mac;
    }

    private static String convertToMac(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            byte b = mac[i];
            int value = 0;
            if (b >= 0 && b < 16) {
                value = b;
                sb.append("0" + Integer.toHexString(value));
            }
            else if (b >= 16) {
                value = b;
                sb.append(Integer.toHexString(value));
            }
            else {
                value = 256 + b;
                sb.append(Integer.toHexString(value));
            }
            if (i != mac.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

    public static boolean isFirstReceiveNetChange() {
        LogUtil.log("isFirstReceiveNetChange : " + FIRST_RECEIVE_CONNECT_CHANGE);
        return FIRST_RECEIVE_CONNECT_CHANGE;

    }

    public static void setNotFirstReceiveNetChange() {
        LogUtil.log("setNotFirstReceiveNetChange false");
        FIRST_RECEIVE_CONNECT_CHANGE = false;
    }

    public static boolean isNetworkConnectState() {
        LogUtil.log("isNetworkConnectState : " + NETWORK_CONNECT_STATE);
        return NETWORK_CONNECT_STATE;
    }

    public static void setNetworkConnectState(boolean nState) {
        LogUtil.log("setNetworkConnectState : " + nState);
        NETWORK_CONNECT_STATE = nState;
    }

    public static void setFirstReceiveNetChange() {
        LogUtil.log("setNotFirstReceiveNetChange true");
        FIRST_RECEIVE_CONNECT_CHANGE = true;
    }

    public static boolean isHasReceviedBootCompleted() {
        return HAS_RECEIVED_BOOT_COMPLETED;
    }

    public static void setHasReceviedBootCompleted(boolean completed) {
        HAS_RECEIVED_BOOT_COMPLETED = completed;
    }

    public static boolean isRecoveryMode() {
        return isRecoveryMode;
    }

    public static void setRecoveryMode(boolean isRecoveryMode) {
        CommonUtil.isRecoveryMode = isRecoveryMode;
    }

    // 联网方式:
    // "wireless" 代表无线
    // "wired" 代表有线
    // "PPPOE" 代表PPPOE
    public static String getNetWorkWay() {
        // return "wired";
        String workWay = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String interName = intf.getDisplayName();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        if (interName.startsWith("eth")) {
                            workWay = "wired";
                        }
                        else if (interName.startsWith("wlan")) {
                            workWay = "wireless";
                        }
                        else {
                            workWay = "PPPOE";
                        }
                    }
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }

        LogUtil.log("getNetWorkWay workWay : " + workWay);
        return workWay;
    }

    /**
     * get properties by android.os.SystemProperties.
     * 
     * @param key
     * @return
     */
    public static String getSystemProperties(String key) {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getDeclaredMethod("get", String.class);
            return (String) method.invoke(clazz.newInstance(), key);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getFreeSize() {

        StatFs sf = new StatFs(getSaveDir());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        long freeSize = availCount * blockSize;

        return freeSize;
    }

    public static void clipCacheDir(String filePath) {

        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    if (null == files || files.length == 0) {
                        file.delete();
                    }
                    else {
                        for (int i = 0; i < files.length; i++) {
                            LogUtil.log("file.getName() : " + files[i].getName());
                            if (!files[i].getName().equals("recovery")) {
                                clipCacheDir(files[i].getAbsolutePath());
                                file.delete();
                            }
                        }
                    }
                }
                else {
                    file.delete();
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getSaveDir() {

        String propDir = getSystemProperties(PROPERTY_DEFAULT_DIR);

        if (!TextUtils.isEmpty(propDir)) {
            return propDir + "/";
        }

        return Environment.getDownloadCacheDirectory().getAbsolutePath() + "/";

    }

    public static String getSaveFilePath() {

        String path = getSaveDir();

        File folder = new File(path);
        if (false == folder.exists()) {
            return null;
        }
        path = path + UPDATEZIP;
        return path;
    }

    public static void cleanOld() {

        String filePath = getSaveFilePath();
        File file = new File(filePath);
        if (file != null && file.exists()) {
            file.delete();
        }

    }

    public static void syncFile(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filePath, "rw");
            file.getFD().sync();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SyncFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String bytesToString(byte[] data) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] temp = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
            temp[i * 2 + 1] = hexDigits[b & 0x0f];
        }
        return new String(temp);
    }

    public static String getMd5String(String str) {  
        
        char[] charArray = str.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
  
        byte[] md5Bytes = null;  
          
        try {  
            md5Bytes = MessageDigest.getInstance("MD5").digest(byteArray);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++) {  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16) {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
  
        return hexValue.toString();  
    }  
    
    public static String getMD5(String filePath) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(filePath);
            byte[] buffer = new byte[8192];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }

            return bytesToString(md.digest());
        }
        catch (Exception ex) {
            LogUtil.log("catch Exception." + ex.getMessage());
            return null;
        }
        finally {
            try {
                fis.close();
            }
            catch (Exception ex) {
                LogUtil.log("catch Exception." + ex.getMessage());
            }
        }
    }

    public static boolean getUpgradeResult(UpgradeTask mTask) {

        String currentVersion = CommonUtil.getSoftVersion();
        int curVersion = -1;
        if (null != currentVersion && currentVersion.length() > 0) {
            try {
                curVersion = Integer.parseInt(currentVersion);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        LogUtil.log("currentVersion:" + currentVersion);

        String newVersion = mTask.getPkgversion();
        int version = -1;
        if (null != newVersion && newVersion.length() > 0) {
            try {
                version = Integer.parseInt(newVersion);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        LogUtil.log("newVersion:" + newVersion);

        return curVersion == version;
    }

    public static boolean getNetworkConnect() {
        NetworkInfo activeNetInfo =
            ((ConnectivityManager) UpgradeApp
                .getInstance()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnected() && activeNetInfo.isAvailable()) {
            return true;
        }
        return false;
    }

    // @SuppressLint("SimpleDateFormat")
    public static boolean getTimeCheck(long currentTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date d = df.parse(timeVerify);

            String currDateTime = df.format(new Date(currentTime));
            LogUtil.log("system curr datetime : " + currDateTime);
            LogUtil.log("verify time : " + timeVerify);

            if (currentTime < d.getTime()) {
                return false;
            }
            return true;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void writeSN2Flash(String server_sn) {

        if (TextUtils.isEmpty(server_sn)) {
            return;
        }

        SkyworthManager mSkyworthManager =
            (SkyworthManager) UpgradeApp.getInstance().getSystemService(Context.SKYWORTH_SERVICE);
        if (mSkyworthManager == null) {
            return;
        }

        String localSN = mSkyworthManager.getSerialId();
        LogUtil.log("localSN : " + localSN + " server SN : " + server_sn);
        if (TextUtils.isEmpty(localSN) || !localSN.equalsIgnoreCase(server_sn)) {
            LogUtil.log("update local SN");
            mSkyworthManager.setSerialId(server_sn);
        }
    }
}
