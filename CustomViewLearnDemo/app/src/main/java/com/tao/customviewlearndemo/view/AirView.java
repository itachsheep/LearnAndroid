package com.tao.customviewlearndemo.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/12.
 */

public class AirView extends View {
    private String TAG = "AirView";
    public AirView(Context context) {
        this(context, null);
    }

    public AirView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    private Paint mPaint;
    private Context mContext;
    public AirView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AirView);
        typedArray.recycle();
        initDrawable();
    }
    private Drawable mDrawable14;
    private Drawable mDrawable13;
    private Drawable mDrawable12;
    private Drawable mDrawable11;
    private Drawable mDrawable10;
    private Drawable mDrawable9;
    private Drawable mDrawable8;
    private void initDrawable() {
        mDrawable8 = getResources().getDrawable(R.drawable.channel1);
        mDrawable9 = getResources().getDrawable(R.drawable.channel2);
        mDrawable10 = getResources().getDrawable(R.drawable.channel3);
        mDrawable11 = getResources().getDrawable(R.drawable.channel4);
        mDrawable12 = getResources().getDrawable(R.drawable.channel5);
        mDrawable13 = getResources().getDrawable(R.drawable.channel6);
        mDrawable14 = getResources().getDrawable(R.drawable.channel7);
    }

    private boolean mIsMoving = false;
    private int mStartAngle = 0;
    private int mCurrentSelect ;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                {
                    mIsMoving = false;
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                {
                    mIsMoving = true;
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    mStartAngle = getStartAngle(mCenterX,mCenterY,x, y);
                    invalidate();
                    return true;
                }
            case MotionEvent.ACTION_UP:
                {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    mStartAngle = getStartAngle(mCenterX,mCenterY,x, y);
                    mIsMoving = false;

                    mCurrentSelect = (int) ((mStartAngle * 1f / 360 * max)+0.5);
                    mStopAngle = mCurrentSelect * 360f / max - 10;
                    Log.i(TAG," mCurrentSelect = "+mCurrentSelect +", mStartAngle = "+mStartAngle);
                    invalidate();
                    return true;
                }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private int mCenterX ;
    private int mCenterY;
    private RectF mOval;
    private float mRadius;
    private float mPicRadius;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//        Log.i(TAG,"onMeasure width = "+width+", height = "+height);
//        Log.i(TAG,"onMeasure left = "+getLeft()+", top = "+getTop()+", right = "+getRight()+", bottom  = "+getBottom());
        mCenterX = width / 2;
        mCenterY = height;
        mRadius = width * 1.0f / 2;
        mPicRadius = mRadius - 20;
        Log.i(TAG,"left = "+getLeft()+", top = "+getTop()+",width = "+width+", height = "+height);
        mOval = new RectF(getLeft(),getTop(),width,height*2);
        mProgressRect = new RectF(sectorRadius,sectorRadius,width - sectorRadius,height * 2 - sectorRadius);
        mTranRect = new RectF(mProgressRect.left+progressWidth,
                mProgressRect.top+progressWidth,
                mProgressRect.right-progressWidth,
                mProgressRect.bottom-progressWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAngle(canvas);
        drawPic(canvas);

    }

    private RectF mProgressRect;
    private int progressWidth = 10;
    private void drawProgress(Canvas canvas,float angle) {
        if(!mIsMoving){
            canvas.save();
            mPaint.setAntiAlias(true);
            mPaint.setARGB(255,26,162,96);
            canvas.drawArc(mProgressRect,angle - 360f  / (2 *max), 360f / max,true,mPaint);
            mPaint.setARGB(255,255,255,255);
            canvas.drawArc(mTranRect,angle - 360f  / (2 *max)-5,360f / max +5,true,mPaint);
            canvas.restore();
        }
    }

    private RectF mTranRect;
    private int sectorRadius = 55;
    private void drawTransSector(Canvas canvas) {
        canvas.save();
//        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setARGB(255,255,255,255);
        canvas.drawArc(mTranRect,0,360,true,mPaint);
        canvas.restore();
    }

    private Drawable mSelectDrawable;
    private float mStopAngle;
    private void drawPic(Canvas c) {
        if(!mIsMoving){
            switch (mCurrentSelect){
                case 8:
                    mSelectDrawable = mDrawable8;
                    break;
                case 9:
                    mSelectDrawable = mDrawable9;
                    break;
                case 10:
                    mSelectDrawable = mDrawable10;
                    break;
                case 11:
                    mSelectDrawable = mDrawable11;
                    break;
                case 12:
                    mSelectDrawable = mDrawable12;
                    break;
                case 13:
                    mSelectDrawable = mDrawable13;
                    break;
                case 14:
                    mSelectDrawable = mDrawable14;
                    break;
            }
            c.save();
            //画扇形
            mPaint.setARGB(255, 185, 164, 130);
            tempStartAngle =  mStopAngle - 360f  / (2 *max);
//            tempStartAngle = mCurrentSelect * 360f / max - 360f/max;
            c.drawArc(mOval, tempStartAngle, 360f / max, true, mPaint);
            c.restore();
            //画选中图标
            if(mSelectDrawable != null){
                int[] pivot = getPivot(mStopAngle);
                int intrinsicWidth = mSelectDrawable.getIntrinsicWidth();
                int intrinsicHeight = mSelectDrawable.getIntrinsicHeight();
                mSelectDrawable.setBounds(pivot[0] - intrinsicWidth / 2,
                        pivot[1] - intrinsicHeight / 2,
                        pivot[0] + intrinsicWidth / 2,
                        pivot[1] + intrinsicHeight / 2);
                mSelectDrawable.draw(c);
            }
            //画进度条
            drawProgress(c,mStopAngle);
        }
    }

    private int max = 14;
//    private int progress  = 1;
    private float tempStartAngle;
    private void drawAngle(Canvas c){
        if(mIsMoving){
            //Log.i(TAG,"drawAngle mStartAngle = "+mStartAngle);
            c.save();
            mPaint.setARGB(150, 185, 164, 130);
            // draw的时候是顺时针，
            tempStartAngle =  mStartAngle - 360f  / (2 * max);
            c.drawArc(mOval, tempStartAngle, 360f  / max, true, mPaint);
            c.restore();
        }
    }

    private int getStartAngle(float centerX, float centerY, float xInView, float yInView){

        double rotation = 0;
        double tan = (centerY - yInView)*1.0 / (xInView - centerX);
        double tmpDegree = Math.atan(tan) / Math.PI * 180;

        /*if (xInView > centerX && yInView < centerY) {  //第一象限
            rotation = tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限
        {
            rotation = 180 + tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限
            rotation = 180 + tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限
            rotation = 270 - tmpDegree;
        } else if (xInView == centerX && yInView < centerY) {
            rotation = 90;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 270;
        }*/
        //顺时针算转过的角度的话
        if (xInView > centerX && yInView < centerY) {  //第一象限
            rotation = 360 - tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限
        {
            rotation = 360 - 180 - tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限
            rotation = 360 - 180 -  tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限
            rotation = 360 - 270 + tmpDegree;
        } else if (xInView == centerX && yInView < centerY) {
            rotation = 270;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 90;
        }
//        Log.i(TAG,"getStartAngle rotation = "+(int)rotation+", tmpDegree = "+(int)tmpDegree
//        +", centenX = "+centerX+", centerY = "+centerY+", xInView = "+xInView+", yInView = "+yInView);
//        Log.i(TAG,"getStartAngle rotation = "+(int)rotation);
        return (int) rotation;
    }

    private int[] getPivot(float degree){
        int[] pivot = new int[2];
        double radians = Math.toRadians(degree);
        /*if(degree >= 0 & degree < 90){//顺时针看，水平线为0度, pivot[0] x方向， pivot[1] y方向
            pivot[0] = (int) (Math.cos(radians) * mRadius + mRadius);
            pivot[1] = (int) (Math.sin(radians) * mRadius + mRadius);
        }
        else if(degree >=90 & degree < 180){
            pivot[0] = (int) (Math.cos(radians) * mRadius + mRadius);
            pivot[1] = (int) (Math.sin(radians) * mRadius + mRadius);
        }
        else if(degree >= 180 & degree < 270){
            pivot[0] = (int) (Math.cos(radians) * mRadius + mRadius);
            pivot[1] = (int) (Math.sin(radians) * mRadius + mRadius);
        }
        else if(degree >= 270 & degree < 360){
            pivot[0] = (int) (Math.cos(radians) * mRadius + mRadius);
            pivot[1] = (int) (Math.sin(radians) * mRadius + mRadius);
        }*/
        pivot[0] = (int) (Math.cos(radians) * mPicRadius + mRadius);
        pivot[1] = (int) (Math.sin(radians) * mPicRadius + mRadius);
//        Log.i(TAG,"getPivot x = "+pivot[0]+", y = "+pivot[1]);
        return pivot;
    }
}
