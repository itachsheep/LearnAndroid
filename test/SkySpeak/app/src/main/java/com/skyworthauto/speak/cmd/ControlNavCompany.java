package com.skyworthauto.speak.cmd;

import android.content.Context;

import com.skyworthauto.speak.R;
import com.txznet.sdk.TXZNavManager;

public class ControlNavCompany extends CmdSpeakable {

    public ControlNavCompany(Context context) {
        super(context, R.array.nav_go_company_array, R.string.nav_go_company_id,
                R.string.nav_go_company_speak);
    }

    @Override
    public void run() {
        TXZNavManager.getInstance().navCompany();
    }

}
