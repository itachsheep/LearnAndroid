package com.skyworthauto.speak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.txznet.sdk.TXZPowerManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent service = new Intent(this, SpeakService.class);
        startService(service);

    }

    public void powerSleep(View v) {
        TXZPowerManager.getInstance()
                .notifyPowerAction(TXZPowerManager.PowerAction.POWER_ACTION_BEFORE_SLEEP);
        TXZPowerManager.getInstance().releaseTXZ();
    }

    public void powerWakeup(View v) {
        TXZPowerManager.getInstance().reinitTXZ(new Runnable() {
            @Override
            public void run() {
                TXZPowerManager.getInstance()
                        .notifyPowerAction(TXZPowerManager.PowerAction.POWER_ACTION_WAKEUP);
            }
        });
    }

    public void onMute(View v) {
        muteVoice(true);
    }

    public void onNoMute(View v) {
        muteVoice(false);
    }

    private void muteVoice(boolean needMute) {
        AudioManager audioManager =
                (AudioManager) SpeakApp.getApp().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, needMute);
    }
}
