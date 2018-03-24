package com.skyworthauto.speak;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by SDT14324 on 2017/11/24.
 */

@SuppressLint("AppCompatCustomView")
public class MyStyleView extends TextView {

    public MyStyleView(Context context) {
        super(context);


    }

    public MyStyleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        }

    public MyStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomStyleView, defStyleAttr, 0);
//        a.getString(R.styleable)
    }
}
