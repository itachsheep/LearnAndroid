package com.tao.adjustview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by SDT14324 on 2018/5/11.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView  extends TextView{

    private String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,getLineCount() * getLineHeight());
    }

    public void setDBCText(String text) {
        Log.i(TAG,"setDBCText ");
        if(text != null){
            String mTextDBC = changToDBC(text);
            setText(mTextDBC);
        }
    }
    private String changToDBC(String text) {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
