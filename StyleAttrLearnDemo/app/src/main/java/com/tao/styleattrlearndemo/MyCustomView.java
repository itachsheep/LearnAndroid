package com.tao.styleattrlearndemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by SDT14324 on 2017/11/28.
 */

public class MyCustomView extends View{
    private String TAG = "MyCustomView";

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {

        //this(context, attrs,0);
        //使用 DefStyle 的属性值
        //this(context, attrs,R.attr.MyCustomViewDefStyleAttr);
        //使用 DefRes 的属性值
        this(context,attrs,0);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);
        //使用 DefStyle 的属性值
        //TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyCustomView, defStyleAttr,0);
        //使用 DefRes 的属性值
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyCustomView,defStyleAttr,R.style.MyCustomViewDefStyleRes);

        String attr1 = ta.getString(R.styleable.MyCustomView_custom_attr1);
        String attr2 = ta.getString(R.styleable.MyCustomView_custom_attr2);
        String attr3 = ta.getString(R.styleable.MyCustomView_custom_attr3);
        String attr4 = ta.getString(R.styleable.MyCustomView_custom_attr4);

        Log.d(TAG, "attr1=" + attr1);
        Log.d(TAG, "attr2=" + attr2);
        Log.d(TAG, "attr3=" + attr3);
        Log.d(TAG, "attr4=" + attr4);
        ta.recycle();
    }
}
