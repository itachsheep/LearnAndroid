package com.tao.styleattrlearndemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by SDT14324 on 2017/11/28.
 */

public class CustomView extends View {
    private String TAG = "CustomView";
    public CustomView(Context context) {
        super(context);
    }
    private static final int[] mAttr = {android.R.attr.text,R.attr.customText,R.attr.noStyleableText};
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.customView_test);
        String customText = typedArray.getString(R.styleable.customView_test_customText);
        String androidText = typedArray.getString(R.styleable.customView_test_android_text);
        Log.i(TAG,"------------------------ use styleable --------------------------");
        Log.d(TAG,"customText : "+customText);
      //  Log.d(TAG,"customText2 : "+customText2);a
        Log.d(TAG,"android text : "+androidText);
        Log.i(TAG,"------------------------ use styleable -------------------------");

       /* TypedArray typedArray = context.obtainStyledAttributes(attrs,mAttr);
        String androidText = typedArray.getString(0);
        @SuppressLint("ResourceType")
        String customText = typedArray.getString(1);
        @SuppressLint("ResourceType")
        String noStyleableText = typedArray.getString(2);
        Log.i(TAG,"------------------------ not use styleable --------------------------");
        Log.d(TAG,"androidText : "+androidText);
        Log.d(TAG,"customText : "+customText);
        Log.d(TAG,"noStyleableText : "+noStyleableText);
        Log.i(TAG,"------------------------ not use styleable -------------------------");*/

        int count = attrs.getAttributeCount();
        for(int i = 0; i < count ; i++){
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            Log.d(TAG,i +"--- "+attrName+" = "+" "+attrValue);
        }

    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
