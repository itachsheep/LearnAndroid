package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.util.L;

import java.util.Arrays;

public abstract class CmdSpeakable extends Cmd {

    public String mSpeakStr;

    public CmdSpeakable(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpeakableCmd, 0, 0);
        mSpeakStr = a.getString(R.styleable.SpeakableCmd_speakWord);
        a.recycle();
    }

    public CmdSpeakable(Context context, int cmdArrId, int cmdId, int speakId) {
        super(context, cmdArrId, cmdId);
        Resources resources = context.getResources();
        mSpeakStr = resources.getString(speakId);
    }

    public void dump() {
        L.d("yqwxxx", "mCmdKey:" + mCmdKey);
        L.d("yqwxxx", "mCmdArray:" + Arrays.toString(mCmdArray));
        L.d("yqwxxx", "mSpeakStr:" + mSpeakStr);
    }
}
