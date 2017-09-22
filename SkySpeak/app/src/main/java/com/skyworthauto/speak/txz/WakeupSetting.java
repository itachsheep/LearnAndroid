package com.skyworthauto.speak.txz;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZConfigManager;

import java.util.Arrays;

public class WakeupSetting {
    private static final String TAG = "SpeakSetting";

    private ContentObserver mKeywordsObserver;
    private final Context mContext;

    public WakeupSetting(Context context) {
        mContext = context;
        mKeywordsObserver = new KeywordsObserver();
    }

    public void initWakeupStates() {
        final ContentResolver resolver = mContext.getContentResolver();
        resolver.registerContentObserver(
                Settings.Global.getUriFor(Constant.KEY_SPEAK_WAKEUP_SWITCH), false,
                mKeywordsObserver);
        resolver.registerContentObserver(Settings.Global.getUriFor(Constant.KEY_SPEAK_WAKEUP_WORD),
                false, mKeywordsObserver);

        TXZConfigManager.getInstance()
                .setUserConfigListener(new TXZConfigManager.UserConfigListener() {

                    @Override
                    public void onChangeWakeupKeywords(String[] strings) {
                        L.d(TAG, "onChangeWakeupKeywords:" + Arrays.toString(strings));
                        saveWakeupKeywords(strings);
                    }

                    @Override
                    public void onChangeCommunicationStyle(String s) {

                    }
                });

        TXZConfigManager.getInstance()
                .getUserWakeupKeywords(new TXZConfigManager.UserKeywordsCallback() {
                    @Override
                    public void result(String[] strings) {
                        L.d(TAG, "getkeywords:" + strings);

                    }
                });

        setWakeupStates();
    }

    private void saveWakeupKeywords(String[] strings) {
        final ContentResolver resolver = mContext.getContentResolver();
        Settings.Global.putString(resolver, Constant.KEY_SPEAK_WAKEUP_WORD, combKeywords(strings));
    }

    private String combKeywords(String[] strings) {
        return Arrays.toString(strings).replace("[", "").replace("]", "").replace(" ", "");
    }

    private void setWakeupStates() {
        final ContentResolver resolver = mContext.getContentResolver();
        String wakeupWord = Settings.Global.getString(resolver, Constant.KEY_SPEAK_WAKEUP_WORD);
        String[] words =
                mContext.getResources().getStringArray(R.array.txz_sdk_init_wakeup_keywords);
        if (!TextUtils.isEmpty(wakeupWord)) {
            words = wakeupWord.split(",");
        }

        L.d(TAG, "updateWakeupStates,words= " + Arrays.toString(words));

        boolean wakeupEnable = Settings.Global
                .getInt(resolver, Constant.KEY_SPEAK_WAKEUP_SWITCH, Constant.WAKEUP_ENABLE)
                == Constant.WAKEUP_ENABLE;

        TXZConfigManager.getInstance().setWakeupKeywordsNew(wakeupEnable ? words : null);
        TXZConfigManager.getInstance().enableChangeWakeupKeywords(wakeupEnable);
    }

    private final class KeywordsObserver extends ContentObserver {
        public KeywordsObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            L.d(TAG, "keywords state change");
            setWakeupStates();
        }
    }
}
