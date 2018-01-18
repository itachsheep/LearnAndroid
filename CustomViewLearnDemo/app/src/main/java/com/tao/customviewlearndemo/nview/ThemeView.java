package com.tao.customviewlearndemo.nview;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
 * Created by SDT14324 on 2018/1/18.
 */

public class ThemeView extends RelativeLayout implements CenterImageView.OnInnerListener {
    private String TAG = ThemeView.class.getSimpleName();

    private float mCenterX ;
    private float mCenterY;
    private float mRadius;

    private ImageView mIvBg;
    private ImageView mIvPart;
    private ImageView mIvProgress;
    private ImageView mIvArrow;
    private CenterImageView mIvCenter;
    private Drawable[] partArray = new Drawable[3];
    private Drawable[] pbArray = new Drawable[3];


    private int curMode = 0;
    private float mArrowEndAngle;
    private float mArrowStartAngle;

    private boolean mEnabled = false;

    public ThemeView(Context context) {
        this(context,null);
    }

    public ThemeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThemeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_theme,this);
        initView(view);
    }

    private void initView(View view) {
        mIvBg = view.findViewById(R.id.th_bg);
        mIvPart = view.findViewById(R.id.th_part);
        partArray[0] = mIvPart.getDrawable();
        partArray[1] = getResources().getDrawable(R.drawable.th_part1);
        partArray[2] = getResources().getDrawable(R.drawable.th_part2);

        mIvCenter = view.findViewById(R.id.th_center);
        mIvProgress = view.findViewById(R.id.th_pb);

        pbArray[0] = mIvProgress.getDrawable();
        pbArray[1] = getResources().getDrawable(R.drawable.th_pb1);
        pbArray[2] = getResources().getDrawable(R.drawable.th_pb2);

        mIvArrow = view.findViewById(R.id.th_arrow);
        mIvCenter.setInnerListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRadius = Math.max(height,width) * 1f / 2;
        mCenterX = mRadius;
        mCenterY = mRadius;
    }

    @Override
    public void onInnerClick(MotionEvent event) {
        setEnableMode();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                float angle = getAngle(mCenterX, mCenterY, x, y);
                Log.i(TAG,"( "+x+","+y+" )"+""+" ------ "+"("+2 * mCenterX+","+2 * mCenterY+")");
                if(x > 2 * mCenterX || y > 2 * mCenterY ){
                    //点击外部 disabled
                    setDisableMode();
                }else {
                    if(!mEnabled){
                        setEnableMode();
                    }else {
                        //根据角度确定模式
                        setAngleAndMode(angle);
                        //选择对应外围圆弧
                        selectPartImageView(curMode);
                        //旋转箭头
                        rotateArrow(mArrowStartAngle,mArrowEndAngle);
                        return true;
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }
    public void setDisableMode(){
        mEnabled = false;

        //隐藏外围背景
        if(mIvBg.getVisibility() == View.VISIBLE){
            mIvBg.setVisibility(View.INVISIBLE);
        }
        //隐藏外围圆弧
        if(mIvPart.getVisibility() == View.VISIBLE){
            mIvPart.setVisibility(View.INVISIBLE);
        }

        //隐藏箭头
        if(mIvArrow.getVisibility() == View.VISIBLE){
            mIvArrow.clearAnimation();
            mIvArrow.setVisibility(View.INVISIBLE);
        }
        //显示进度
        if(mIvProgress.getVisibility() == View.INVISIBLE){
            mIvProgress.setImageDrawable(pbArray[curMode]);
            mIvProgress.setVisibility(View.VISIBLE);
        }
    }

    public void setEnableMode(){
        mEnabled = true;
        //show 外围背景
        if(mIvBg.getVisibility() == View.INVISIBLE){
            mIvBg.setVisibility(View.VISIBLE);
        }
        //show 外围圆弧
        if(mIvPart.getVisibility() == View.INVISIBLE){
            mIvPart.setVisibility(View.VISIBLE);
        }

        //show 箭头
        if(mIvArrow.getVisibility() == View.INVISIBLE){
            rotateArrow(mArrowStartAngle,mArrowEndAngle);
            mIvArrow.setVisibility(View.VISIBLE);
        }

        //hide 进度条
        if(mIvProgress.getVisibility() == View.VISIBLE){
            mIvProgress.setVisibility(View.INVISIBLE);
        }
    }

    private void rotateArrow(float from, float to) {
        if(mIvArrow != null ){
            RotateAnimation animation = new RotateAnimation(from,to,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setFillAfter(true);
            animation.setDuration(10);
            mIvArrow.startAnimation(animation);
            mArrowStartAngle = mArrowEndAngle;
        }
    }
    private void selectPartImageView(int mode) {
        mIvPart.setImageDrawable(partArray[mode]);
    }

    private void setAngleAndMode(float angle) {
        if(angle >0  && angle <= 60 || angle > 300){
            curMode = 0;
            mArrowEndAngle = 0;
        }else if(angle > 60 && angle <= 180){
            curMode = 1;
            mArrowEndAngle = 120;
        }else if(angle > 180 && angle <= 300){
            curMode = 2;
            mArrowEndAngle = 240;
        }
    }

    private float getAngle(float centerX, float centerY, float xInView, float yInView) {
        double rotation = 0;
        double tan = (centerY - yInView) * 1.0 / (xInView - centerX);
        double tmpDegree = Math.atan(tan) / Math.PI * 180;
        //顺时针算转过的角度的话
        if (xInView > centerX && yInView < centerY) {  //第一象限  tmpDegree: 90 , 0
            rotation = 270 - tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限 tmpDegree: 0 , -90
        {
            rotation = 90 - tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限 tmpDegree: 90, 0
            rotation = 90 - tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限 tmpDegree: 0 , -90
            rotation = 290 - tmpDegree;
        } else if (xInView == centerX && yInView < centerY) {//
            rotation = 180;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 0;
        } else if (yInView == centerY && xInView > centerX) {
            rotation = 270;
        } else if (yInView == centerY && xInView < centerX) {
            rotation = 90;
        }
        return (float) rotation;
    }


}
