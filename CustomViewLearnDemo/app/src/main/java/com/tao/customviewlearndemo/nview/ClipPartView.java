package com.tao.customviewlearndemo.nview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/16.
 */

@SuppressLint("AppCompatCustomView")
public class ClipPartView extends ImageView {
    private String TAG = "ClipPartView";
    private final int STYLE_LIGHT_DELAY = 1;
    private final int STYLE_PATTERN_SETTING = 2;
    private Drawable mDrawable;
    private SectorPartDrawable sectorPartDrawable;
    private int mStyle;

    public ClipPartView(Context context) {
        this(context,null);
    }

    public ClipPartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClipPartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClipPartView);
        mDrawable = typedArray.getDrawable(R.styleable.ClipPartView_cpvSrc);
        mStyle = typedArray.getInteger(R.styleable.ClipPartView_cpvStyle,STYLE_LIGHT_DELAY);
        typedArray.recycle();
        sectorPartDrawable = new SectorPartDrawable(mDrawable,mStyle);
        this.setImageDrawable(sectorPartDrawable);
    }

    public void setProgrss(float progrss){
        sectorPartDrawable.setPercent(progrss);
    }


}
