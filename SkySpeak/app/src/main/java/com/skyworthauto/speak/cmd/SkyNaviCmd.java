package com.skyworthauto.speak.cmd;

import android.content.Context;

public abstract class SkyNaviCmd extends CmdSpeakable {

    private SkyNaviExecutor mSkyNaviExecutor;

    public SkyNaviCmd(Context context, int cmdArrId, int cmdId, int speakId,
            SkyNaviExecutor skyNaviExecutor) {
        super(context, cmdArrId, cmdId, speakId);
        mSkyNaviExecutor = skyNaviExecutor;
    }

    public SkyNaviExecutor getNaviExecutor() {
        return mSkyNaviExecutor;
    }

}
