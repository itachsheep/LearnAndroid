package com.tao.customviewlearndemo.nview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/16.
 */

public class SectorPartRoot extends RelativeLayout implements CenterImageView.OnInnerListener{
    private String TAG = "SectorPartRoot";

    private float mRadius;
    private float mInnerRadius;
    private float mCenterX ;
    private float mCenterY;

    private ClipPartView clipPartView;
    private CenterImageView centerImageView;
    private TextView tvNum;
    private int mode = 0;
    private float mProgress;
    public SectorPartRoot(Context context) {
        this(context,null);
    }

    public SectorPartRoot(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectorPartRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_setor_holder,this);
        clipPartView = view.findViewById(R.id.spr_cpv);
        centerImageView = view.findViewById(R.id.spr_center_iv);
        tvNum = view.findViewById(R.id.spr_tv_num);
        initListener();
        //初始化时显示开始的进度
        initClipPartView();
    }

    private void initClipPartView() {
        clipPartView.setProgrss(30f/360);
    }

    private void initListener() {
        centerImageView.setInnerListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRadius = Math.max(height,width) * 1f / 2;
        mInnerRadius = mRadius - dp2px(30);
        mCenterX = mRadius;
        mCenterY = mRadius;
        Log.i(TAG,"onMeasure height = "+height+", width = "+width);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,"dispatchTouchEvent ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG,"onInterceptTouchEvent ");
        return super.onInterceptTouchEvent(ev);
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
                setAngleAndMode(angle);
                Log.i(TAG,"action down mProgress = "+mProgress+", angle = "+angle);
                if(clipPartView != null){
                    clipPartView.setProgrss(mProgress);
                }
                updateTvNum();
                break;
            }
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setAngleAndMode(float angle) {
        if(angle >0  && angle <= 30){
            mProgress = 30f / 360;
            mode = 0;
        }else if(angle > 30 && angle <= 90){
            mProgress = 90f / 360;
            mode = 15;
        }else if(angle > 90 && angle <= 150){
            mProgress = 150f / 360;
            mode = 30;
        }else if(angle > 150 && angle <= 240){
            mProgress = 240f / 360;
            mode = 45;
        }else if(angle > 240 && angle <= 360){
            mProgress = 330f /360;
            mode = 60;
        }
    }

    private void showClipPartView(){
        if (clipPartView.getVisibility() == View.INVISIBLE){
            clipPartView.setVisibility(View.VISIBLE);
        }
    }
    public void hideClipPartView(){
        if(clipPartView.getVisibility() == View.VISIBLE){
            clipPartView.setVisibility(View.INVISIBLE);
        }
        // 当ClipPartView不可见时，显示进度条
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void onInnerClick(MotionEvent event) {
        showClipPartView();
    }

    private void updateTvNum( ) {
        tvNum.setText(mode + "s");
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
