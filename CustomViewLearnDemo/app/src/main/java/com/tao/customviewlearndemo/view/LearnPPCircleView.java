package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;

import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by SDT14324 on 2018/1/14.
 */

public class LearnPPCircleView extends View {
    private String TAG = "LearnPPCircleView";
    private int mLayoutSize = 100;//整个控件的尺寸（方形）
    private Paint mPaint;//绘制线条
    private Paint mSmallPaint;//绘制分数
    private int mCenterX;
    private int mColorBackground;

    private int mColorText;//主要颜色
    private int mBgColor;//背景
    private int mColorGray;//灰色进度条
    private int mColorRed;//灰色进度条
    private int mColorLine;//直线颜色

    private float now = 0; //当前的进度
    private String mNowScoreString ;
    private int mPart = 15;
    private float mSection = 360f / mPart;
    private String strprogress = "100"; //显示的进度
    private float progress = 0; //显示的进度

    private float outArcWidth;
    private RectF mOutRect;
    private RectF mInnerRect;

    public LearnPPCircleView(Context context) {
        this(context,null);
    }

    public LearnPPCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LearnPPCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mColorBackground = context.getResources().getColor(R.color.colorPrimary);
        mColorText = context.getResources().getColor(R.color.yellow);
        mBgColor = context.getResources().getColor(R.color.ppCircleViewBg);

        mColorGray = context.getResources().getColor(R.color.ppCircleColorGray);
        mColorRed = context.getResources().getColor(R.color.ppCircleColorRed);
        mColorLine = context.getResources().getColor(R.color.ppLineColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        mLayoutSize = min(widthSpecSize, heightSpecSize);
        if (mLayoutSize == 0) {
            mLayoutSize = max(widthSpecSize, heightSpecSize);
        }
        Log.i(TAG,"onMeasure mLayoutSize = "+mLayoutSize);
        setMeasuredDimension(mLayoutSize, mLayoutSize);

        mCenterX = mLayoutSize / 2;
        mPaint = new Paint();
        mSmallPaint = new Paint();

        mOutRect = new RectF(0,0,mLayoutSize,mLayoutSize);
        outArcWidth = dip2px(30);
        mInnerRect = new RectF(outArcWidth,outArcWidth,mLayoutSize - outArcWidth,mLayoutSize - outArcWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //画背景圆
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBgColor);
        canvas.drawCircle(mCenterX,mCenterX,mCenterX,mPaint);

        int endR = (int) (360 * (now / 100) / 15) * 15;

        //画进度圆
        float gap = dip2px(20);
        mPaint.setColor(mColorRed);
        canvas.drawArc(mOutRect, -90, endR, true, mPaint);


        //画线，许多的线，15度画一条，线的宽度是2dp
        mPaint.setColor(mColorLine);
        mPaint.setStrokeWidth(dip2px(2));
        for (int r = 0; r < 360; r = r + mPart) {
            /*canvas.drawLine(mCenterX + (float) ((mCenterX - gap) * sin(toRadians(r))),
                    mCenterX - (float) ((mCenterX - gap) * cos(toRadians(r))),
                    mCenterX - (float) ((mCenterX - gap) * sin(toRadians(r))),
                    mCenterX + (float) ((mCenterX - gap) * cos(toRadians(r))), mPaint);*/

            canvas.drawLine((float) (mCenterX +  (mCenterX ) * cos(toRadians(r))),
                    (float) (mCenterX + (mCenterX ) * sin(toRadians(r))),
                    (float) (mCenterX +  (mCenterX ) * cos(toRadians(r + 180))),
                    (float) (mCenterX + (mCenterX ) * sin(toRadians(r + 180))),mPaint);
        }

        //画填充背景
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterX,mCenterX,mCenterX - gap,mPaint);
        //到此，背景绘制完毕


        //写百分比
        mNowScoreString = (int) now + "";
        if ("".equals(strprogress)) {
            mPaint.setColor(mColorText);
            mPaint.setStrokeWidth(dip2px(2));
            canvas.drawLine(mCenterX * 0.77f, mCenterX, mCenterX * 0.95f, mCenterX, mPaint);
            canvas.drawLine(mCenterX * 1.05f, mCenterX, mCenterX * 1.23f, mCenterX, mPaint);
        } else {
            mPaint.setColor(mColorText);
            mPaint.setTextSize(mLayoutSize / 4f);//控制文字大小
            //Paint paint2 = new Paint();
            mSmallPaint.setAntiAlias(true);
            mSmallPaint.setTextSize(mLayoutSize / 12);//控制文字大小
            mSmallPaint.setColor(mColorText);
            canvas.drawText(mNowScoreString,
                    mCenterX - 0.5f * (mPaint.measureText(mNowScoreString)),
                    mCenterX - 0.5f * (mPaint.ascent() + mPaint.descent()),
                    mPaint);
            canvas.drawText("分",
                    mCenterX + 0.5f * (mPaint.measureText((int) now + "") + mSmallPaint.measureText("分")),
                    mCenterX - 0.05f * (mPaint.ascent() + mPaint.descent()),
                    mSmallPaint);
        }

        //外部小球
        mCenterX = getWidth() / 2;
        canvas.drawCircle(mCenterX + (float) ((mCenterX * 0.95f) * Math.sin(Math.toRadians(endR))),
                mCenterX - (float) ((mCenterX * 0.95f) * Math.cos(Math.toRadians(endR))), mCenterX * 0.04f, mPaint);

        Path p = new Path();
        p.moveTo(mCenterX + (float) ((mCenterX * 0.86f) * Math.sin(Math.toRadians(endR))),
                mCenterX - (float) ((mCenterX * 0.86f) * Math.cos(Math.toRadians(endR))));

        p.lineTo(mCenterX + (float) ((mCenterX * 0.94f) * Math.sin(Math.toRadians(endR + 2.5))),
                mCenterX - (float) ((mCenterX * 0.94f) * Math.cos(Math.toRadians(endR + 2.5))));
        p.lineTo(mCenterX + (float) ((mCenterX * 0.94f) * Math.sin(Math.toRadians(endR - 2.5))),
                mCenterX - (float) ((mCenterX * 0.94f) * Math.cos(Math.toRadians(endR - 2.5))));
        p.close();
        canvas.drawPath(p, mPaint);

//        if (now < progress - 1) {
//            now = now + 1;
//            postInvalidate();
//        } else if (now < progress) {
//            now = (int) progress;
//            postInvalidate();
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                Log.i(TAG,"action down");
                float x = event.getX();
                float y = event.getY();
                int angle = getAngle(mCenterX, mCenterX, x, y);
                float progress = 100f * angle / 360;
                now = progress;
                invalidate();
                return true;
            }

            case MotionEvent.ACTION_MOVE:
            {
                Log.i(TAG,"action move !!");
                float x = event.getX();
                float y = event.getY();
                int angle = getAngle(mCenterX, mCenterX, x, y);
                float progress = 100f * angle / 360;
                now = progress;
                invalidate();
                return true;
            }
            case MotionEvent.ACTION_UP:
            {
                Log.i(TAG,"action up ");

                return true;
            }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 外部回调
     *
     * @param strprogress 显示调进度文字，如果是""，或者null了，则显示两条横线
     * @param progress    进度条调进度
     * @param isAnim      进度条是否需要动画
     */
    public void setProgress(String strprogress, float progress, boolean isAnim) {
        if (strprogress == null) {
            this.strprogress = "";
        } else {
            this.strprogress = strprogress;
        }
        this.now = 0;
        this.progress = progress;


        if (!isAnim) {
            now = progress;
        }
        postInvalidate();
    }

    private int getAngle(float centerX, float centerY, float xInView, float yInView){

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
            rotation = 90 - tmpDegree;
        } else if (xInView < centerX && yInView < centerY) //第二象限
        {
            rotation = 270 - tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限
            rotation = 270 -  tmpDegree;
        } else if (xInView > centerX && yInView > centerY) { //第四象限
            rotation = -tmpDegree + 90;
        } else if (xInView == centerX && yInView < centerY) {
            rotation = 360;
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 180;
        }else if(yInView == centerY && xInView < centerX){
            rotation = 270;
        }else if(yInView == centerY && xInView > centerX){
            rotation = 90;
        }
//        Log.i(TAG,"getStartAngle rotation = "+(int)rotation+", tmpDegree = "+(int)tmpDegree
//        +", centenX = "+centerX+", centerY = "+centerY+", xInView = "+xInView+", yInView = "+yInView);
//        Log.i(TAG,"getStartAngle rotation = "+(int)rotation+", tmpDegree = "+tmpDegree);
        return (int) rotation;
    }

    private int dip2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
