package com.skyworthauto.speak.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SkyPreferenceManager {

    private static final String SKYWORTHAUTO_AIR_INFO = "sky_speak_info";

    private static SharedPreferences sSharedPreferences;

    public static void init(Context context) {
        sSharedPreferences =
                context.getSharedPreferences(SKYWORTHAUTO_AIR_INFO, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sSharedPreferences.getBoolean(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return sSharedPreferences.getFloat(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return sSharedPreferences.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return sSharedPreferences.getLong(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return sSharedPreferences.getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        sSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void putFloat(String key, float value) {
        sSharedPreferences.edit().putFloat(key, value).apply();
    }

    public static void putInt(String key, int value) {
        sSharedPreferences.edit().putInt(key, value).apply();
    }

    public static void putLong(String key, long value) {
        sSharedPreferences.edit().putLong(key, value).apply();
    }

    public static void putString(String key, String value) {
        sSharedPreferences.edit().putString(key, value).apply();
    }

    public static void remove(String key) {
        sSharedPreferences.edit().remove(key);
    }

}
