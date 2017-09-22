package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.util.ISpeakControl;
import com.skyworthauto.speak.util.L;

public class IntentCmd extends CmdSpeakable {
    private static final String TAG = "IntentCmd";
    private String mAction;
    private String mSubAction;
    private int mValue;

    public IntentCmd(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IntentCmd, 0, 0);

        mAction = a.getString(R.styleable.IntentCmd_action);
        mSubAction = a.getString(R.styleable.IntentCmd_subAction);
        mValue = a.getInt(R.styleable.IntentCmd_value, -1);
        a.recycle();
    }

    public IntentCmd(Context context, int cmdArrId, int cmdId, int speakId, int action,
            int subAction, int value) {
        super(context, cmdArrId, cmdId, speakId);
        mAction = context.getString(action);
        mSubAction = context.getString(subAction);
        mValue = context.getResources().getInteger(value);
    }

    @Override
    public void run() {
        L.d(TAG, "run: mAction=" + mAction + ", mSubAction=" + mSubAction + ", mValue=" + mValue);

        Intent intent = new Intent(mAction);
        intent.putExtra(ISpeakControl.EXTRA_ACTION, mSubAction);
        intent.putExtra(ISpeakControl.EXTRA_VALUE, mValue);
        SpeakApp.getApp().sendBroadcast(intent);
    }
}
