package com.tao.customviewlearndemo.nview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;

/**
 * Created by taowei on 2018/1/14.
 * 2018-01-14 22:33
 * CustomViewLearnDemo
 * com.tao.customviewlearndemo.view
 */

@SuppressLint("AppCompatCustomView")
public class ClipDrawView extends View {
    private Paint mPaint;
    private String TAG = "ClipDrawView";
    private Drawable mDrawable;

    private int mLayoutSize;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private float mStartAngle = 0;
    private float mEndAngle ;

    public ClipDrawView(Context context) {
        this(context, null);
    }

    public ClipDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClipDrawView);
        mDrawable = typedArray.getDrawable(R.styleable.ClipDrawView_cdvSrc);
        typedArray.recycle();
        mPaint = new Paint();

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mLayoutSize = Math.max(width, height);
        Log.i(TAG, "width = " + width + ",height = " + height + "onMeasure mLayoutSize = " + mLayoutSize);
        mCenterY = mLayoutSize * 1f / 2;
        mCenterX = mLayoutSize * 1f / 2;
        mRadius = mLayoutSize * 1f / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {


            }
            case MotionEvent.ACTION_MOVE:
            {
                float x = event.getX();
                float y = event.getY();
                mEndAngle = getAngle(mCenterX, mCenterY, x, y);
                invalidate();
            }
                break;
        }
        return super.onTouchEvent(event);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();

        //系统默认以圆心为原点坐标，向右x轴正方向为0度，往下顺时针旋转增大 45,90,135 ..
        path.moveTo(mCenterX,mCenterY); //圆心
        path.lineTo((float)(mCenterX + mRadius * Math.cos( Math.toRadians(mStartAngle))),
                (float)(mCenterY + mRadius * Math.sin( Math.toRadians(mStartAngle))));
        path.lineTo(2 * mCenterX,2 * mCenterY);
        if(mEndAngle > 90){
            path.lineTo(mCenterX,2 * mCenterY);
            path.lineTo(0,2 * mCenterY);
        }
//        if(mEndAngle > 135){
//            path.lineTo(0,2 * mCenterY);
//        }
        if(mEndAngle > 180){
            path.lineTo(0,mCenterY);
            path.lineTo(0,0);
        }
//        if(mEndAngle > 225){
//            path.lineTo(0,0);
//        }
        if(mEndAngle > 270){
            path.lineTo(mCenterX,0);
            path.lineTo(2*mCenterX ,0);
        }
//        if(mEndAngle > 315){
//            path.lineTo(2*mCenterX ,0);
//        }
        path.lineTo((float)(mCenterX + mRadius * Math.cos(Math.toRadians(mEndAngle))),
                (float)(mCenterY + mRadius * Math.sin(Math.toRadians(mEndAngle))));
        path.close();
        canvas.clipPath(path);
        mDrawable.setBounds(new Rect(0, 0, (int) (2 * mRadius), (int)( 2 * mRadius)));
        mDrawable.draw(canvas);
    }

    private float getAngle(float centerX, float centerY, float xInView, float yInView) {
        double rotation = 0;
        double tan = (centerY - yInView) * 1.0 / (xInView - centerX);
        double tmpDegree = Math.atan(tan) / Math.PI * 180;
        //以圆心为原点坐标，向右x轴正方向为0度，往下顺时针旋转增大 45,90,135 ..
        if (xInView > centerX && yInView < centerY) {  //第一象限  tmpDegree: 90 , 0
            rotation = 360 - tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限 tmpDegree: 0 , -90
        {
            rotation = 180 - tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限 tmpDegree: 90, 0
            rotation = 180 - tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限 tmpDegree: 0 , -90
            rotation = - tmpDegree;
        } else if (xInView == centerX && yInView < centerY) {//
            rotation = 180;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 0;
        } else if (yInView == centerY && xInView > centerX) {
            rotation = 270;
        } else if (yInView == centerY && xInView < centerX) {
            rotation = 90;
        }
        //Log.i(TAG,"getAngle rotation = "+rotation+", tmpDegree = "+tmpDegree);
        return (float) rotation;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
