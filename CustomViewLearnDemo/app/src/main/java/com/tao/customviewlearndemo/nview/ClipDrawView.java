package com.tao.customviewlearndemo.nview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tao.customviewlearndemo.R;

/**
 * Created by taowei on 2018/1/14.
 * 2018-01-14 22:33
 * CustomViewLearnDemo
 * com.tao.customviewlearndemo.view
 */

@SuppressLint("AppCompatCustomView")
public class ClipDrawView extends ImageView {
    private String TAG = "ClipDrawView";
    private Drawable mBgDrawable;
    private SectorEntireDrawable mBgSectorEntireDrawable;
    private int mLayoutSize;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private float mPercent;

    public ClipDrawView(Context context) {
        this(context, null);
    }

    public ClipDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClipDrawView);
        mBgDrawable = typedArray.getDrawable(R.styleable.ClipDrawView_cdvSrc);
        typedArray.recycle();
        mBgSectorEntireDrawable = new SectorEntireDrawable(mBgDrawable);
        this.setImageDrawable(mBgSectorEntireDrawable);
    }

    public void setPercent(float progress){
        mPercent = progress;
        mBgSectorEntireDrawable.setPercent(progress);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mLayoutSize = Math.max(width, height);
        Log.i(TAG, "width = " + width
                + ",height = " + height
                + "onMeasure mLayoutSize = " + mLayoutSize);
        mCenterY = mLayoutSize * 1f / 2;
        mCenterX = mLayoutSize * 1f / 2;
        mRadius = mLayoutSize * 1f / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                float x = event.getX();
                float y = event.getY();
                float angle = getAngle(mCenterX, mCenterY, x, y);
                mPercent = angle / 360;
                Log.i(TAG,"action down mPercent = "+mPercent);
                setPercent(mPercent);
            }
                break;
            case MotionEvent.ACTION_MOVE:
            {
                float x = event.getX();
                float y = event.getY();
                float angle = getAngle(mCenterX, mCenterY, x, y);
                mPercent = angle / 360;
                Log.i(TAG,"action down mPercent = "+mPercent);
                setPercent(mPercent);
            }
                break;

            case MotionEvent.ACTION_UP:
            {

            }
                break;
        }
        return super.onTouchEvent(event);
    }

    private float getAngle(float centerX, float centerY, float xInView, float yInView){
        double rotation = 0;
        double tan = (centerY - yInView)*1.0 / (xInView - centerX);
        double tmpDegree = Math.atan(tan) / Math.PI * 180;
        //顺时针算转过的角度的话
        if (xInView > centerX && yInView < centerY) {  //第一象限  tmpDegree: 90 , 0
            rotation = 270 - tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限 tmpDegree: 0 , -90
        {
            rotation = 90 - tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限 tmpDegree: 90, 0
            rotation = 90 -  tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限 tmpDegree: 0 , -90
            rotation = 290 - tmpDegree;
        } else if (xInView == centerX && yInView < centerY) {//
            rotation = 180;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 0;
        }else if(yInView == centerY && xInView > centerX){
            rotation = 270;
        }else if(yInView == centerY && xInView < centerX) {
            rotation = 90;
        }
//        Log.i(TAG,"getAngle rotation = "+(int)rotation+", tmpDegree = "+tmpDegree);
        return (float) rotation;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
