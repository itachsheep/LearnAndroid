package com.tao.customviewlearndemo.nview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/17.
 */

public class AtmosphereLightView extends RelativeLayout {
    private String TAG = "AtmosphereLightView";
    private int curMode = 0;
    private int lastMode =0;
    private float mArrowEndAngle;
    private float mArrowStartAngle;
    private float mProgress;

    private float mCenterX ;
    private float mCenterY;
    private float mRadius;
    private boolean mShowArrow = true;//是否显示箭头

    private ImageView mIvArrow;
    private ImageView[] mIvArray = new ImageView[7];
    public AtmosphereLightView(Context context) {
        this(context,null);
    }

    public AtmosphereLightView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AtmosphereLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_atmosphere,this);
        mIvArrow = view.findViewById(R.id.atmos_arrow);
        mIvArray[0] = view.findViewById(R.id.atmos_part0);
        mIvArray[1] = view.findViewById(R.id.atmos_part1);
        mIvArray[2] = view.findViewById(R.id.atmos_part2);
        mIvArray[3] = view.findViewById(R.id.atmos_part3);
        mIvArray[4] = view.findViewById(R.id.atmos_part4);
        mIvArray[5] = view.findViewById(R.id.atmos_part5);
        mIvArray[6] = view.findViewById(R.id.atmos_part6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRadius = Math.max(height,width) * 1f / 2;
        mCenterX = mRadius;
        mCenterY = mRadius;
        Log.i(TAG,"onMeasure height = "+height+", width = "+width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                float angle = getAngle(mCenterX, mCenterY, x, y);
                Log.i(TAG,"onTouchEvent angle = "+angle);
                //根据角度确定模式
                setAngleAndMode(angle);
                //选择对应外围圆弧
                if(mShowArrow){
                    selectOuterArc(curMode);
                }
                //旋转箭头
                if(mIvArrow != null && mShowArrow){
                    rotateArrow(mArrowStartAngle,mArrowEndAngle);
                    mArrowStartAngle = mArrowEndAngle;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void selectOuterArc(int sMode) {
        mIvArray[lastMode].setVisibility(View.INVISIBLE);
        mIvArray[sMode].setVisibility(View.VISIBLE);
        lastMode = sMode;
    }

    private void rotateArrow(float from, float to) {
        RotateAnimation animation = new RotateAnimation(from,to,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        animation.setFillAfter(true);
        animation.setDuration(10);
        mIvArrow.startAnimation(animation);
    }

    private void setAngleAndMode(float angle) {
        if(angle >0  && angle <= 25.5){
            mProgress = 25.5f / 360;
            curMode = 0;
            mArrowEndAngle = 0;
        }else if(angle > 25.5 && angle <= 76.5){
            mProgress = 76.5f / 360;
            curMode = 1;
            mArrowEndAngle = 51;
        }else if(angle > 76.5 && angle <= 127.5){
            mProgress = 127.5f / 360;
            curMode = 2;
            mArrowEndAngle = 102;
        }else if(angle > 127.5 && angle <= 181.5){
            mProgress = 181.5f / 360;
            curMode = 3;
            mArrowEndAngle = 154.5f;
        }else if(angle > 181.5 && angle <= 232.5){
            mProgress = 232.5f / 360;
            curMode = 4;
            mArrowEndAngle = 207;
        }else if(angle > 232.5 && angle <= 283.5){
            mProgress = 283.5f / 360;
            curMode = 5;
            mArrowEndAngle = 258f;
        }else if(angle > 283.5 && angle <= 334.5){
            mProgress = 334.5f / 360;
            curMode = 6;
            mArrowEndAngle = 309;
        }
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
