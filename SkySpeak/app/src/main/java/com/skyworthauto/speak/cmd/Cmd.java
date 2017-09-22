package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.skyworthauto.speak.R;

public abstract class Cmd implements Runnable {

    public String[] mCmdArray;
    public String mCmdKey;

    protected Context mContext;

    public Cmd(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Cmd, 0, 0);
        mCmdKey = a.getString(R.styleable.Cmd_key);
        CharSequence[] words = a.getTextArray(R.styleable.Cmd_wakeupWords);
        a.recycle();

        mCmdArray = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            mCmdArray[i] = words[i].toString();
        }
    }

    public Cmd(Context context, int cmdArrId, int cmdKey) {
        mContext = context;
        Resources resources = context.getResources();
        mCmdArray = resources.getStringArray(cmdArrId);
        mCmdKey = resources.getString(cmdKey);
    }

    public void onRunBefore() {

    }

}
