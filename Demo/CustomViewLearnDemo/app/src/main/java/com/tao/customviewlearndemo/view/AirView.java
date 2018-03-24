package com.tao.customviewlearndemo.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/12.
 */

public class AirView extends View {
    private String TAG = "AirView";

    private Paint mPaint;
    private Context mContext;

    private int max = 14;//圆弧划分段数
    private float halfSection = 360f  / (2 * max);
    private float totalSection = 360f / max;
    private float tempStartAngle;

    private Drawable mDrawable14;
    private Drawable mDrawable13;
    private Drawable mDrawable12;
    private Drawable mDrawable11;
    private Drawable mDrawable10;
    private Drawable mDrawable9;
    private Drawable mDrawable8;
    private Drawable mArrowDrawable;

    private boolean mIsMoving = false;
    private int mStartAngle = 0;
    private int mCurrentSelect ;

    private int mCenterX ;
    private int mCenterY;




    private RectF mTranRect;
    private int sectorRadius = dp2px(50);

    private Drawable mSelectDrawable;//要绘制的图片
    private float mStopAngle;//触摸后停止的角度


    private int outerArcWidth = dp2px(50);
    private RectF mOutArcRect;//用于绘制圆弧的外围矩形区域
    private int mOutArcWidthVar = dp2px(28);

    private int progressWidth = dp2px(10);
    private RectF mProgressRect;//
    private int progressWidthVar = dp2px(28);

    private float mRadiusOrigin;//原始图形半径
    private float mRadiusOuterArc;//外面圆弧的半径
    private float mRadiusPic;//图标中心所在半径
    private float mWidthArcToPic = dp2px(25);//图标距离外围距离


    private OnAirModeChangeListener mAirModeChangeListener;

    public AirView(Context context) {
        this(context, null);
    }

    public AirView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AirView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AirView);
        typedArray.recycle();
        initDrawable();
    }

    private void initDrawable() {
        mDrawable8 = getResources().getDrawable(R.drawable.channel1);
        mDrawable9 = getResources().getDrawable(R.drawable.channel2);
        mDrawable10 = getResources().getDrawable(R.drawable.channel3);
        mDrawable11 = getResources().getDrawable(R.drawable.channel4);
        mDrawable12 = getResources().getDrawable(R.drawable.channel5);
        mDrawable13 = getResources().getDrawable(R.drawable.channel6);
        mDrawable14 = getResources().getDrawable(R.drawable.channel7);
        mArrowDrawable = getResources().getDrawable(R.drawable.arrow);
    }


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

                    mCurrentSelect = (int) Math.round ((mStartAngle * 1f / 360 * max)+0.5);
                    mStopAngle = mCurrentSelect * totalSection - halfSection;
                    Log.i(TAG," mCurrentSelect = "+mCurrentSelect +", mStartAngle = "+mStartAngle);
                    invalidate();
                    return true;
                }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//        Log.i(TAG,"onMeasure width = "+width+", height = "+height);
//        Log.i(TAG,"onMeasure left = "+getLeft()+", top = "+getTop()+", right = "+getRight()+", bottom  = "+getBottom());

        mCenterX = width / 2;
        mCenterY = height;

        Log.i(TAG,"left = "+getLeft()+", top = "+getTop()+",width = "+width+", height = "+height);
        mRadiusOrigin = width / 2;
        //绘制最外面圆弧
        mOutArcRect = new RectF(getLeft() + mOutArcWidthVar,
                getTop() + mOutArcWidthVar,
                width - mOutArcWidthVar,
                height*2 - mOutArcWidthVar);

        //绘制圆弧的半径
        mRadiusOuterArc = mOutArcRect.width() / 2;
        //图标所在半径
        mRadiusPic = mRadiusOrigin - mWidthArcToPic;

        mProgressRect = new RectF(
                mOutArcRect.left + progressWidthVar,
                mOutArcRect.top + progressWidthVar,
                mOutArcRect.right - progressWidthVar,
                mOutArcRect.bottom - progressWidthVar);

        mTranRect = new RectF(mProgressRect.left+progressWidth,
                mProgressRect.top+progressWidth,
                mProgressRect.right-progressWidth,
                mProgressRect.bottom-progressWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawSectorWithStrartAngle(canvas);
        if(mIsMoving){
            drawArcWithStartAngle(canvas,mStartAngle);
        }
        drawPic(canvas);
    }

    private void drawProgress(Canvas c,float angle) {
        /*if(!mIsMoving){
            canvas.save();
            mPaint.setAntiAlias(true);
            mPaint.setARGB(255,26,162,96);
            canvas.drawArc(mProgressRect,angle - 360f  / (2 *max), 360f / max,true,mPaint);
            mPaint.setARGB(255,255,255,255);
            canvas.drawArc(mTranRect,angle - 360f  / (2 *max)-5,360f / max +5,true,mPaint);
            canvas.restore();
        }*/
        c.save();
        mPaint.setARGB(255,26,162,96);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressWidth);
        c.drawArc(mProgressRect,angle - halfSection,totalSection,false,mPaint);
        c.restore();
    }


    private void drawTransSector(Canvas canvas) {
        canvas.save();
//        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setARGB(255,255,255,255);
        canvas.drawArc(mTranRect,0,360,true,mPaint);
        canvas.restore();
    }

    private void drawPic(Canvas c) {
        if(!mIsMoving){
            //根据停止角度选中对应图标
            selectPostionDrawable();


           /* c.save();
            mPaint.setARGB(255, 185, 164, 130);
            tempStartAngle =  mStopAngle - 360f  / (2 *max);
            //tempStartAngle = mCurrentSelect * 360f / max - 360f/max;
            c.drawArc(mOutArcRect, tempStartAngle, 360f / max, true, mPaint);
            c.restore();*/
            //画扇形
           //drawArcWithStartAngle(c,mStopAngle);
           drawNoTransArcWithStartAngle(c,mStopAngle);
            //画选中图标
            if(mSelectDrawable != null){
                int[] pivot = getPicCirclePivot(mStopAngle);
                int intrinsicWidth = mSelectDrawable.getIntrinsicWidth();
                int intrinsicHeight = mSelectDrawable.getIntrinsicHeight();
                mSelectDrawable.setBounds(pivot[0] - intrinsicWidth / 2,
                        pivot[1] - intrinsicHeight / 2,
                        pivot[0] + intrinsicWidth / 2,
                        pivot[1] + intrinsicHeight / 2);
                mSelectDrawable.draw(c);
            }
            //画进度条，画圆弧
            drawProgress(c,mStopAngle);
            //画箭头
            drawArrow(c,mStopAngle);
        }
    }

    private void drawArrow(Canvas c, float angle) {
        //使用接口，交给外面选转箭头
        if(mAirModeChangeListener != null){
            mAirModeChangeListener.onAirModeChange(0,angle);
        }
    }

    public interface OnAirModeChangeListener{
        void onAirModeChange(int mode, float angle);
    }

    public void setAirModeChangeListener(OnAirModeChangeListener listener){
        mAirModeChangeListener = listener;
    }

    private void selectPostionDrawable() {
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
    }

    //画透明圆弧
    private void drawArcWithStartAngle(Canvas c,float angle){
        c.save();
        mPaint.setARGB(150, 185, 164, 130);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(outerArcWidth);
        c.drawArc(mOutArcRect,angle - halfSection,totalSection,false,mPaint);
        c.restore();
    }

    //画非透明圆弧
    private void drawNoTransArcWithStartAngle(Canvas c,float angle){
        c.save();
        mPaint.setARGB(255, 185, 164, 130);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(outerArcWidth);
        c.drawArc(mOutArcRect,angle - halfSection,totalSection,false,mPaint);
        c.restore();
    }

    //画扇形
    private void drawSectorWithStrartAngle(Canvas c){
        if(mIsMoving){
            //Log.i(TAG,"drawSectorWithStrartAngle mStartAngle = "+mStartAngle);
            c.save();
            mPaint.setARGB(150, 185, 164, 130);
            // draw的时候是顺时针，
            c.drawArc(mOutArcRect, mStartAngle - halfSection, totalSection, true, mPaint);
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
            rotation = -tmpDegree;
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

    //根据角度，得到角度在 图标所在圆的上的坐标
    private int[] getPicCirclePivot(float degree){
        //顺时针看，水平线为0度, pivot[0] x方向， pivot[1] y方向
        int[] pivot = new int[2];
        double radians = Math.toRadians(degree);
        /*if(degree >= 0 & degree < 90){
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
//        pivot[0] = (int) (Math.cos(radians) * mRadiusOrigin + mRadiusOrigin);
//        pivot[1] = (int) (Math.sin(radians) * mRadiusOrigin + mRadiusOrigin);
        pivot[0] = (int) (Math.cos(radians) * mRadiusPic + mRadiusOrigin);
        pivot[1] = (int) (Math.sin(radians) * mRadiusPic + mRadiusOrigin);
        Log.i(TAG,"getPicCirclePivot x = "+pivot[0]+", y = "+pivot[1]+",degree = "+degree);
        return pivot;
    }

    private int dp2px(int dp) {
        /*DisplayMetrics dm  = getResources().getDisplayMetrics();
        float density = dm.density;
        int densityDPI = dm.densityDpi;
        int px = (int) (dp * density);
        Log.i(TAG,"dp2px density = "+density+", densityDPI = "+densityDPI+", px = "+px);*/

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
}
