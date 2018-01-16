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
    private float mProgress;
    private float mCenterX;
    private float mCenterY;
    private OnProgressListener mProgressListener;
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
        mProgress = progrss;
        sectorPartDrawable.setPercent(mProgress);
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
    public interface OnProgressListener{
        void onProgressChange(float progress);
    }

    public void setProgressListener(OnProgressListener listener){
        mProgressListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                float x = event.getX();
                float y = event.getY();
                float angle = getAngle(mCenterX, mCenterY, x, y);
                if(angle >0  && angle <= 30){
                    mProgress = 30f / 360;
                }else if(angle > 30 && angle <= 90){
                    mProgress = 90f / 360;
                }else if(angle > 90 && angle <= 150){
                    mProgress = 150f / 360;
                }else if(angle > 150 && angle <= 240){
                    mProgress = 240f / 360;
                }else if(angle > 240 && angle <= 360){
                    mProgress = 330f /360;
                }
                Log.i(TAG,"action down mProgress = "+mProgress+", angle = "+angle);
                setProgrss(mProgress);
                if(null != mProgressListener){
                    mProgressListener.onProgressChange(angle);
                }
                break;
            }

            case MotionEvent.ACTION_UP:

                break;
            default:
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
}
