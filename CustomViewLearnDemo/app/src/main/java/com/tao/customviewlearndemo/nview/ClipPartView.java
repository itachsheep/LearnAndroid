package com.tao.customviewlearndemo.nview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/16.
 */

@SuppressLint("AppCompatCustomView")
public class ClipPartView extends ImageView {

    private String TAG = "ClipPartView";
    private float mRadius;
    private Drawable mDrawable;
    private SectorPartDrawable sectorPartDrawable;

    private float mCenterX;
    private float mCenterY;

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
        typedArray.recycle();
        Log.i(TAG,"ClipPartView mDrawable = "+mDrawable);
        //mDrawable = context.getResources().getDrawable(R.drawable.normal_bg);
        sectorPartDrawable = new SectorPartDrawable(mDrawable);
        this.setImageDrawable(sectorPartDrawable);
    }

    public void setProgrss(float progrss){
        sectorPartDrawable.setPercent(progrss);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.i(TAG,"onMeasure width = "+width+", height = "+height);
        mRadius = Math.min(width,height) * 1f / 2;
        mCenterY = mRadius;
        mCenterX = mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }


}
