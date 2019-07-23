package com.tao.wei.testjni;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ShellHolderView extends View {
    static {
        System.loadLibrary("native-lib");
    }

    public ShellHolderView(Context context) {
        this(context,null);
    }

    public ShellHolderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShellHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public int add(int a,int b){
        return nativeAdd(a,b);
    }

    public float add(float a,float b){
        return nativeAdd(a,b);
    }

    public native int nativeAdd(int a, int b);
    public native float nativeAdd(float a, float b);

}
