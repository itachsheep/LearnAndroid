package com.skyworthauto.speak.cmd;

import android.content.Intent;

import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.util.ISpeakControl;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZAsrManager;

public class RadioControl {

    private static final String TAG = "RadioControl";

    public static void registerAM() {
        boolean success = TXZAsrManager.getInstance().regCommandForAM(522, 1620, "OPEN_AM_FREQ");
        L.d(TAG, "registerAM :" + success);
        if (success) {
            TXZAsrManager.getInstance().addCommandListener(new TXZAsrManager.CommandListener() {

                @Override
                public void onCommand(String cmd, String data) {
                    L.d(TAG, "rAM onCommand cmd:" + cmd + ", data=" + data);
                    if (data.startsWith("OPEN_AM_FREQ#")) {
                        int am = Integer.parseInt(data.substring("OPEN_AM_FREQ#".length()));
                        setAmTo(am);
                    }
                }
            });
        }
    }


    public static void registerFM() {
        boolean success =
                TXZAsrManager.getInstance().regCommandForFM(87.5f, 108.0f, "OPEN_FM_FREQ");
        L.d(TAG, "registerFM :" + success);
        if (success) {
            TXZAsrManager.getInstance().addCommandListener(new TXZAsrManager.CommandListener() {

                @Override
                public void onCommand(String cmd, String data) {
                    L.d(TAG, "rFM onCommand cmd:" + cmd + ", data=" + data);
                    if (data.startsWith("OPEN_FM_FREQ#")) {
                        float fm = Float.parseFloat(data.substring("OPEN_FM_FREQ#".length()));
                        setFmTo(fm);
                    }
                }
            });
        }
    }


    public static void openRadio() {
        ISpeakControl.openOrCloseApp(ISpeakControl.ACTION_CONTROL_RADIO, true);
    }

    public static void closeRadio() {
        ISpeakControl.openOrCloseApp(ISpeakControl.ACTION_CONTROL_RADIO, false);
    }

    public static void prevChannel() {
        controlChannel(ISpeakControl.PREV_CHANNEL);
    }

    public static void nextChannel() {
        controlChannel(ISpeakControl.NEXT_CHANNEL);
    }

    public static void searchChannel() {
        controlChannel(ISpeakControl.SEARCH_CHANNEL);
    }

    private static void controlChannel(int value) {
        Intent intent = ISpeakControl
                .createIntent(ISpeakControl.ACTION_CONTROL_RADIO, ISpeakControl.CTRL_CHANNEL,
                        value);
        SpeakApp.getApp().sendBroadcast(intent);
    }

    private static void switchToFm() {
        switchChannel(ISpeakControl.SWITCH_TO_FM);
    }

    private static void switchToAm() {
        switchChannel(ISpeakControl.SWITCH_TO_AM);
    }

    private static void switchChannel(int value) {
        Intent intent = ISpeakControl
                .createIntent(ISpeakControl.ACTION_CONTROL_RADIO, ISpeakControl.SWITCH_CHANNEL,
                        value);
        SpeakApp.getApp().sendBroadcast(intent);
    }

    public static void setFmTo(float value) {
        Intent intent = ISpeakControl.createIntent(ISpeakControl.ACTION_CONTROL_RADIO,
                ISpeakControl.FREQUENCY_MODULATION, value);
        SpeakApp.getApp().sendBroadcast(intent);
    }

    public static void setAmTo(int value) {
        Intent intent = ISpeakControl.createIntent(ISpeakControl.ACTION_CONTROL_RADIO,
                ISpeakControl.AMPLITUDE_MODULATION, value);
        SpeakApp.getApp().sendBroadcast(intent);
    }

}
