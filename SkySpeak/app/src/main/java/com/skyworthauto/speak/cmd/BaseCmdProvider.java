package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.util.L;

import java.util.ArrayList;
import java.util.Collection;

public class BaseCmdProvider implements ICmdProvider {

    private ArrayList<CmdSpeakable> mList = new ArrayList<>();

    protected Context mContext;
    protected int mId;

    public BaseCmdProvider(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CmdProvider);
        mId = a.getInt(R.styleable.CmdProvider_id, -1);
        a.recycle();
    }

    public void dump() {
        L.d("yqwxx", "dump BaseCmdProvider:");
        L.d("yqwxx", "mId:" + mId);
        for (CmdSpeakable cmd : mList) {
            cmd.dump();
        }
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public Collection<? extends CmdSpeakable> getList() {
        return mList;
    }

    public void addChild(CmdSpeakable child) {
        mList.add(child);
    }

    public int size() {
        return mList.size();
    }

    @Override
    public ICommand getCommand(CmdSpeakable cmdData) {
        return new CustomCommand(cmdData);
    }
}
