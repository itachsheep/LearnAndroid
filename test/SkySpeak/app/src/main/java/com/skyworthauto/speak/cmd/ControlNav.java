package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.skyworthauto.speak.util.L;

public class ControlNav extends CmdSpeakable {
    private static final String TAG = ControlNav.class.getSimpleName();

    private static final String ACTION = "CLD.NAVI.MSG.VOICEORDER";
    private static final String VOICEORDER_ARRAY_PARAM = "VOICEORDER_ARRAY_PARAM";
    private static final String ACTION_TYPE = "VOICEORDER_ACTION_TYPE";

    private String mContent;
    private int mType;

    public ControlNav(Context context, int cmdArrId, int cmdId, int speakId, int type,
            String content) {
        super(context, cmdArrId, cmdId, speakId);
        mType = type;
        mContent = content;
    }

    @Override
    public void run() {
        sendVoiceOrderMessage(mType, mContent);
    }

    private void sendVoiceOrderMessage(int actiontype, String content) {
        L.d(TAG, "send message,type=" + actiontype + " , content=" + content);
        if (TextUtils.isEmpty(content)) {
            content = "";
        }

        String[] param = new String[2];
        param[0] = "1.0";
        param[1] = content;

        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_TYPE, actiontype);
        bundle.putStringArray(VOICEORDER_ARRAY_PARAM, param);

        Intent intent = new Intent(ACTION);
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);
    }

}
