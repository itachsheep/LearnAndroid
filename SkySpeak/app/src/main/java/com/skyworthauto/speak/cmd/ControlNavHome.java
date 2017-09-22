package com.skyworthauto.speak.cmd;

import android.content.Context;

import com.skyworthauto.speak.R;
import com.txznet.sdk.TXZNavManager;

public class ControlNavHome extends CmdSpeakable {

    public ControlNavHome(Context context) {
        super(context, R.array.nav_go_home_array, R.string.nav_go_home_id,
                R.string.nav_go_home_speak);
    }

    @Override
    public void run() {
        TXZNavManager.getInstance().navHome();
    }

}
