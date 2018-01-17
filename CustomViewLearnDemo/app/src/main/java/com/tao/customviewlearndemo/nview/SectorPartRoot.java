package com.tao.customviewlearndemo.nview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/16.
 */

public class SectorPartRoot extends RelativeLayout implements CenterImageView.OnInnerListener{
    private String TAG = "SectorPartRoot";

    private float mRadius;
    private float mCenterX ;
    private float mCenterY;

    private ClipPartView clipPartViewOut;
    private ClipPartView clipPartViewIn;
    private CenterImageView centerImageView;
    private ImageView imageViewBg;
    private ImageView ivArrow;

    private TextView tvNum;
    private int mode = 0;
    private float mArrowEndAngle;
    private float mArrowStartAngle;
    private float mProgress;

    private boolean mShowArrow = true;

    public SectorPartRoot(Context context) {
        this(context,null);
    }

    public SectorPartRoot(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectorPartRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_setor_holder,this);
        clipPartViewOut = view.findViewById(R.id.spr_cpv_out);
        clipPartViewIn = view.findViewById(R.id.spr_cpv_in);
        centerImageView = view.findViewById(R.id.spr_center_iv);
        tvNum = view.findViewById(R.id.spr_tv_num);
        imageViewBg = view.findViewById(R.id.spr_bg);
        ivArrow = view.findViewById(R.id.spr_iv_arrow);

        initListener();
        //初始化时显示开始的进度
        initClipPartView();
    }

    private void initClipPartView() {
        clipPartViewOut.setProgrss(25.5f/360);
        clipPartViewIn.setProgrss(25.5f/360);
        clipPartViewIn.setVisibility(INVISIBLE);
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
                if(clipPartViewOut != null){
                    clipPartViewOut.setProgrss(mProgress);
                }
                if(clipPartViewIn != null){
                    clipPartViewIn.setProgrss(mProgress);
                }
                if(ivArrow != null && mShowArrow){
                    rotateArrow(mArrowStartAngle,mArrowEndAngle);
                    mArrowStartAngle = mArrowEndAngle;
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

    private void rotateArrow(float from, float to) {
        RotateAnimation animation = new RotateAnimation(from,to,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        animation.setFillAfter(true);
        animation.setDuration(10);
        ivArrow.startAnimation(animation);
    }


    private void setAngleAndMode(float angle) {
        if(angle >0  && angle <= 25.5){
            mProgress = 25.5f / 360;
            mode = 0;
            mArrowEndAngle = 0;
        }else if(angle > 25.5 && angle <= 76.5){
            mProgress = 76.5f / 360;
            mode = 1;
            mArrowEndAngle = 51;
        }else if(angle > 76.5 && angle <= 127.5){
            mProgress = 127.5f / 360;
            mode = 2;
            mArrowEndAngle = 102;
        }else if(angle > 127.5 && angle <= 181.5){
            mProgress = 181.5f / 360;
            mode = 3;
            mArrowEndAngle = 154.5f;
        }else if(angle > 181.5 && angle <= 232.5){
            mProgress = 232.5f / 360;
            mode = 4;
            mArrowEndAngle = 207;
        }else if(angle > 232.5 && angle <= 283.5){
            mProgress = 283.5f / 360;
            mode = 5;
            mArrowEndAngle = 258f;
        }else if(angle > 283.5 && angle <= 334.5){
            mProgress = 334.5f / 360;
            mode = 6;
            mArrowEndAngle = 309;
        }
    }

    private void showClipPartView(){
        //show 外围背景
        if(imageViewBg.getVisibility() == View.INVISIBLE){
            imageViewBg.setVisibility(View.VISIBLE);
        }
        //show 外围黄色圆弧
        if (clipPartViewOut.getVisibility() == View.INVISIBLE){
            clipPartViewOut.setVisibility(View.VISIBLE);
        }

        //show 箭头
        if(ivArrow.getVisibility() == View.INVISIBLE){
            rotateArrow(mArrowStartAngle,mArrowEndAngle);
            ivArrow.setVisibility(View.VISIBLE);
        }
        mShowArrow = true;

        // hide 内部进度条
        if(clipPartViewIn.getVisibility() == View.VISIBLE){
            clipPartViewIn.setVisibility(View.INVISIBLE);
        }
    }
    public void hideClipPartView(){
        //hide 外围背景
        if(imageViewBg.getVisibility() == View.VISIBLE){
            imageViewBg.setVisibility(View.INVISIBLE);
        }
        //hide 外围黄色圆弧
        if(clipPartViewOut.getVisibility() == View.VISIBLE){
            clipPartViewOut.setVisibility(View.INVISIBLE);
        }

        //hide 箭头
        if(ivArrow.getVisibility() == View.VISIBLE){
            ivArrow.clearAnimation();
            ivArrow.setVisibility(View.INVISIBLE);
        }
        mShowArrow = false;

        // show 内部进度条
        if(clipPartViewIn.getVisibility() == View.INVISIBLE){
            clipPartViewIn.setVisibility(View.VISIBLE);
        }
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
