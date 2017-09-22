package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.Intent;

import com.skyworthauto.sdk.define.SkyBroadcast;
import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.mcu.McuServiceManager;
//import com.skyworthauto.speak.mcu.MusicPlayerManager;
import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZMusicManager;

public class OtherCmds {

    private static final String TAG = "OtherCmds";

    public static class OpenWifi extends OpenActivity {

        public OpenWifi(Context context) {
            super(context, R.array.wifi_open_array, R.string.wifi_open_id, R.string.wifi_open_speak,
                    R.integer.setting_surface_id, R.string.wifi_open_package_name,
                    R.string.wifi_open_class_name);
        }

        @Override
        public void run() {
            super.run();
            openWifi();
        }

        @Override
        protected Intent createIntent() {
            Intent intent = new Intent();
            intent.setClassName(mPackageName, mClassName);
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            return intent;
        }

        private void openWifi() {
            L.d(TAG, "openWifi");
            //            WifiManager wifiManager = (WifiManager) mContext.getSystemService
            // (Context.WIFI_SERVICE);
            //            int wifiApState = wifiManager.getWifiApState();
            //            if ((wifiApState == WifiManager.WIFI_AP_STATE_ENABLING) || (wifiApState
            //                    == WifiManager.WIFI_AP_STATE_ENABLED)) {
            //                wifiManager.setWifiApEnabled(null, false);
            //            }
            //            wifiManager.setWifiEnabled(true);
        }

    }

    public static class CloseBluetooth extends CmdSpeakable {

        public CloseBluetooth(Context context) {
            super(context, R.array.bluetooth_close_array, R.string.bluetooth_close_id,
                    R.string.bluetooth_close_speak);
        }

        @Override
        public void run() {
            L.d(TAG, "close bluetooth");
            Intent intent = new Intent(SkyBroadcast.SKY_VOICE_CTROL_CLOSE_BT);
            mContext.sendBroadcast(intent);
        }

    }

    public static class CloseMusic extends CmdSpeakable {

        public CloseMusic(Context context) {
            super(context, R.array.music_close_array, R.string.music_close_id,
                    R.string.music_close_speak);
        }

        @Override
        public void run() {
            L.d(TAG, "close CloseMusic");
            if (McuServiceManager.getInstance().isInLocalMusicSource()) {
                closeLocalMusic();
            } else if (McuServiceManager.getInstance().isInOnlineMusicSource()) {
                closeOnlineMusic();
            }
        }

        private void closeLocalMusic() {
            //            MusicPlayerManager.pause();

            Intent intent = new Intent(SkyBroadcast.SKY_CLOSE_MUSIC);
            SpeakApp.getApp().sendBroadcast(intent);
        }

        private void closeOnlineMusic() {
            TXZMusicManager.getInstance().pause();
            TXZMusicManager.getInstance().exit();
        }

    }

}
