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
 * Created by SDT14324 on 2018/1/15.
 */

public class NormalCircleView extends RelativeLayout {

    private String TAG = "NormalCircleView";
    private ImageView mMoveSector;
    private ImageView mIvCenter;
    private float mRadius;
    private int mStartAngle;
    private int mStopAngle;
    public NormalCircleView(Context context) {
        this(context,null);
    }

    public NormalCircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NormalCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_normal_circle,this);
        mMoveSector = view.findViewById(R.id.move_sector);
        mIvCenter = view.findViewById(R.id.nc_center);
        /*mIvCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"iv center clicked !!");
            }
        });*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRadius = Math.min(height,width) * 1f / 2;
        Log.i(TAG,"width = "+width+", height = "+height+", mRadius = "+mRadius);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                float x = event.getX();
                float y = event.getY();
                int angle = getAngle(mRadius, mRadius, x, y);
                rotateSectorView(mStartAngle,angle);
                mStartAngle = angle;
                return true;
            }
            case MotionEvent.ACTION_MOVE:
            {
                float x = event.getX();
                float y = event.getY();
                int angle = getAngle(mRadius, mRadius, x, y);
                rotateSectorView(mStartAngle,angle);
                mStartAngle = angle;
                return true;
            }
            case MotionEvent.ACTION_UP:
            {
                float x = event.getX();
                float y = event.getY();
                int angle = getAngle(mRadius, mRadius, x, y);
                if( angle <45 || angle > 315){
                    mStopAngle = 0;
                }else if(angle > 45 && angle < 135){
                    mStopAngle = 90;
                }else if(angle > 135 && angle < 225){
                    mStopAngle = 180;
                }else if(angle > 225 && angle < 315){
                    mStopAngle = 270;
                }
                rotateSectorView(mStartAngle,mStopAngle);
                mStartAngle = mStopAngle;
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void rotateSectorView(float from,float end) {
        RotateAnimation rotateAnimation = new RotateAnimation(from,end,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(10);
        mMoveSector.startAnimation(rotateAnimation);
    }

    private int getAngle(float centerX, float centerY, float xInView, float yInView){
        double rotation = 0;
        double tan = (centerY - yInView)*1.0 / (xInView - centerX);
        double tmpDegree = Math.atan(tan) / Math.PI * 180;
        //顺时针算转过的角度的话
        if (xInView > centerX && yInView < centerY) {  //第一象限
            rotation = 360 - tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限
        {
            rotation = 360 - 180 - tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限
            rotation = 360 - 180 -  tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限
            rotation = -tmpDegree;
        } else if (xInView == centerX && yInView < centerY) {
            rotation = 270;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 90;
        }
//        Log.i(TAG,"getAngle rotation = "+(int)rotation);
        return (int) rotation;
    }

}
