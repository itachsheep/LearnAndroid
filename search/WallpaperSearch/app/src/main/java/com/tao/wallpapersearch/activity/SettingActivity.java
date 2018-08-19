package com.tao.wallpapersearch.activity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.tao.wallpapersearch.L;
import com.tao.wallpapersearch.R;

import java.awt.font.TextAttribute;

public class SettingActivity extends PreferenceActivity {

    private String TAG = SettingActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        Preference numberOfCircles = getPreferenceScreen().findPreference("numberOfCircles");
        numberOfCircles.setOnPreferenceChangeListener(numberCheckListener);

        Preference touch = getPreferenceScreen().findPreference("touch");
        touch.setOnPreferenceChangeListener(tocuchCheckListener);
    }

    Preference.OnPreferenceChangeListener numberCheckListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            L.i(TAG,"onPreferenceChange preference:"+preference
                    +", newValue:"+newValue);
            if (newValue != null && newValue.toString().length() > 0
                    && newValue.toString().matches("\\d*")) {
                return true;
            }
            // If now create a message to the user
            Toast.makeText(SettingActivity.this, "Invalid Input",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    Preference.OnPreferenceChangeListener tocuchCheckListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            L.i(TAG,"onPreferenceChange preference:"+preference
                    +", newValue:"+newValue);
//            if (newValue != null && newValue.toString().length() > 0
//                    && newValue.toString().matches("\\d*")) {
//                return true;
//            }
            // If now create a message to the user
            Toast.makeText(SettingActivity.this, "newValue:"+newValue,
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    };
}
