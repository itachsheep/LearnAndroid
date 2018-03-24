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
 * Created by SDT14324 on 2018/1/17.
 */

public class AtmosphereLightView extends RelativeLayout implements CenterImageView.OnInnerListener {
    private String TAG = "AtmosphereLightView";
    private int curMode = 0;
    private int lastMode =0;
    private float mArrowEndAngle;
    private float mArrowStartAngle;
//    private float mProgress;

    private float mCenterX ;
    private float mCenterY;
    private float mRadius;
    private boolean mEnabled = false;//是否显示箭头

    private ImageView mIvArrow;
    private ImageView[] mOutPartArray = new ImageView[7];
    private Drawable[] mCenterPartArray = new Drawable[7];
    private Drawable mCenterPartOrigin;
    private CenterImageView mIvCenterPart;
    private ImageView mAtmosBg;

    public AtmosphereLightView(Context context) {
        this(context,null);
    }

    public AtmosphereLightView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AtmosphereLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_atmosphere,this);
        initView(view);
        initListener();
    }

    private void initListener() {
        mIvCenterPart.setInnerListener(this);
    }

    private void initView(View view) {
        mIvArrow = view.findViewById(R.id.atmos_arrow);
        mIvCenterPart = view.findViewById(R.id.atmos_center_part);
        mIvCenterPart = view.findViewById(R.id.atmos_center_part);
        mAtmosBg = view.findViewById(R.id.atmos_bg);

        mOutPartArray[0] = view.findViewById(R.id.atmos_part0);
        mOutPartArray[1] = view.findViewById(R.id.atmos_part1);
        mOutPartArray[2] = view.findViewById(R.id.atmos_part2);
        mOutPartArray[3] = view.findViewById(R.id.atmos_part3);
        mOutPartArray[4] = view.findViewById(R.id.atmos_part4);
        mOutPartArray[5] = view.findViewById(R.id.atmos_part5);
        mOutPartArray[6] = view.findViewById(R.id.atmos_part6);

        mCenterPartArray[0] = getResources().getDrawable(R.drawable.atoms_center0);
        mCenterPartArray[1] = getResources().getDrawable(R.drawable.atoms_center1);
        mCenterPartArray[2] = getResources().getDrawable(R.drawable.atoms_center2);
        mCenterPartArray[3] = getResources().getDrawable(R.drawable.atoms_center3);
        mCenterPartArray[4] = getResources().getDrawable(R.drawable.atoms_center4);
        mCenterPartArray[5] = getResources().getDrawable(R.drawable.atoms_center5);
        mCenterPartArray[6] = getResources().getDrawable(R.drawable.atoms_center6);
        mCenterPartOrigin = getResources().getDrawable(R.drawable.atmos_center_bg);
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
    public void onInnerClick(MotionEvent event) {
        showOutPart();
    }

    public void showOutPart(){
        //show 箭头
        if(mIvArrow.getVisibility() == View.INVISIBLE){
            rotateArrow(mArrowStartAngle,mArrowEndAngle);
            mIvArrow.setVisibility(View.VISIBLE);
        }
        mEnabled = true;

        //show 背景
        if(mAtmosBg.getVisibility() == View.INVISIBLE){
            mAtmosBg.setVisibility(View.VISIBLE);
        }

        //show 外围
        if(mOutPartArray[curMode].getVisibility() == View.INVISIBLE){
            mOutPartArray[curMode].setVisibility(View.VISIBLE);
        }

        //显示中间部分
        mIvCenterPart.setImageDrawable(mCenterPartOrigin);
    }

    public void init(){
        hideOutPart();
    }

    public void hideOutPart() {
        //隐藏箭头
        if(mIvArrow.getVisibility() == View.VISIBLE){
            mIvArrow.clearAnimation();
            mIvArrow.setVisibility(View.INVISIBLE);
        }
        mEnabled = false;

        //隐藏背景
        if(mAtmosBg.getVisibility() == View.VISIBLE){
            mAtmosBg.setVisibility(View.INVISIBLE);
        }

        //隐藏外围
        if(mOutPartArray[curMode].getVisibility() == View.VISIBLE){
            mOutPartArray[curMode].setVisibility(View.INVISIBLE);
        }

        //显示中间部分
        mIvCenterPart.setImageDrawable(mCenterPartArray[curMode]);
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
                    //点击外部消失
                    hideOutPart();
                }else {
                    if(mEnabled){
                        //根据角度确定模式
                        setAngleAndMode(angle);
                        //选择对应外围圆弧
                        selectOuterArc(curMode);
                        //旋转箭头
                        rotateArrow(mArrowStartAngle,mArrowEndAngle);
                        return true;
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    public void setMode(int mode){

    }

    private void selectOuterArc(int sMode) {
        mOutPartArray[lastMode].setVisibility(View.INVISIBLE);
        mOutPartArray[sMode].setVisibility(View.VISIBLE);
        lastMode = sMode;
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

    private void setAngleAndMode(float angle) {
        if(angle >0  && angle <= 25.5 || angle > 334.5){
            //mProgress = 25.5f / 360;
            curMode = 0;
            mArrowEndAngle = 0;
        }else if(angle > 25.5 && angle <= 76.5){
            //mProgress = 76.5f / 360;
            curMode = 1;
            mArrowEndAngle = 51;
        }else if(angle > 76.5 && angle <= 127.5){
            //mProgress = 127.5f / 360;
            curMode = 2;
            mArrowEndAngle = 102;
        }else if(angle > 127.5 && angle <= 181.5){
            //mProgress = 181.5f / 360;
            curMode = 3;
            mArrowEndAngle = 154.5f;
        }else if(angle > 181.5 && angle <= 232.5){
            //mProgress = 232.5f / 360;
            curMode = 4;
            mArrowEndAngle = 207;
        }else if(angle > 232.5 && angle <= 283.5){
            //mProgress = 283.5f / 360;
            curMode = 5;
            mArrowEndAngle = 258f;
        }else if(angle > 283.5 && angle <= 334.5){
            //mProgress = 334.5f / 360;
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
