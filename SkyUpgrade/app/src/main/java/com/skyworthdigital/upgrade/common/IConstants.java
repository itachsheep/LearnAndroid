package com.skyworthdigital.upgrade.common;

import android.net.Uri;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public interface IConstants {
    public static final long DAY_LONG = 8 * 60 * 60 * 1000;
    public static final String ACTION_AUTO_CHECK = "skyworth.intent.action.OTA_AUTO_CHECK";

    //check from server, error code
    public static final int ERR_CODE_NO_SPACE = 1;
    public static final int ERR_CODE_NETWORK = 2;
    public static final int ERR_CODE_MD5_ERR = 3;
    public static final int MSG_NETWORK_INVALID = 4;
    public static final int MSG_NO_VERSION = 5;
    public static final int MSG_FOUND_VERSION = 6;
    public static final int MSG_SYSTEM_HAS_ROOT = 7;


    //pkg type
    public static final int FULL_PACKAGE = 1;
    public static final int INCREASE_PACKAGE = 2;
    public static final int PATCH_PACKAGE = 3;


    public static final int DOWLOAD_TIMES = 6;

    //recovery
    public static final String ACTION_HINT_RECOVERY = "android.action.skyworthdigital.ota.hintreadyrecovery";
    public static String DESCRIPTION = "description";
    public static String ISFORCEUPGRADE = "isforceupgrade";
    public static String PKGVERSION = "pkgversion";

    //deviceinfo
    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String AUTHORITY = "com.skyworthdigital.upgrade";
    public static final String CONTENT_PATH = "deviceinfo";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    //report upgrade success
    public static final int UPLOAD_UPGRADE_RESULT_TIMES = 3;
    public static final String ACTION_UPGRADE_COMPLETE = "android.action.skyworthdigital.ota.upgradecomplete";
    public static String ISUPGRADERESULT = "isupgraderesult";

    //time unit
    public static final int SECONDS_1 = 1000 ;
    public static final int SECONDS_5 = 5000 ;
    public static final int SECONDS_10 = 10000 ;
    public static final int SECONDS_15 = 15000 ;
    public static final int SECONDS_20 = 20000 ;
    public static final int SECONDS_60 = 60000 ;
    public static final int SECONDS_90 = 90000 ;
    public static final int SECONDS_120 = 120000 ;

    //test
    public static final boolean IS_TEST_MODE = true;

}
