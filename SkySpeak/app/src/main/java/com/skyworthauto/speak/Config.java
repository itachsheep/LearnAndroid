package com.skyworthauto.speak;

import android.os.SystemProperties;

import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.Utils;

public class Config {
    private static final String TAG = "Config";

    private static String sBtType;

    public static void init() {
        sBtType = SystemProperties.get(Constant.RO_PRODUCT_SKYBLUETOOTH, Constant.INVALID);
        sBtType = Constant.OUTSIDE;
        L.d(TAG, "btType==" + sBtType);
    }

    public static boolean isInnerBt() {
        return Constant.INNER.equals(sBtType);
    }

    public static boolean isOutsideBt() {
        return Constant.OUTSIDE.equals(sBtType);
    }

    public static boolean isInvalidBt() {
        return Constant.INVALID.equals(sBtType);
    }

    public static boolean useSkyNavi() {
        return false;
    }

    public static boolean useCldNavi() {
        return Utils.isPackageInstalled(SpeakApp.getApp(), Constant.PACKAGE_NAME_CLD_NAVI_QR200,
                Constant.PACKAGE_NAME_CLD_NAVI_QR210);
    }

    public static boolean needShowHelpInfos() {
        return true;
    }
}
