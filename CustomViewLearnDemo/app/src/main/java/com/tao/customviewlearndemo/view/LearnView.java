package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/11.
 */

public class LearnView extends View {
    private String TAG = "LearnView";
    private CharSequence[] rangeColorArray;
    private CharSequence[] rangeValueArray;
    private CharSequence[] rangeTextArray;
    private int borderColor ;
    private int cursorColor;
    private int extraTextColor;
    private int rangeTextSize; //中间文本大小
    private int mSection = 0; // 等分份数
    private int mSparkleWidth; // 指示标宽度
    private int mCalibrationWidth; // 刻度圆弧宽度
    private Paint mPaint;
    private RectF mRectFProgressArc;
    private RectF mRectFCalibrationFArc;
    private Rect mRectText;
    private int mBackgroundColor;

    public LearnView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        Log.i(TAG," LearnView 1--") ;
    }

    public LearnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleRangeView);
        rangeColorArray = typedArray.getTextArray(R.styleable.CircleRangeView_rangeColorArray);
        rangeValueArray = typedArray.getTextArray(R.styleable.CircleRangeView_rangeValueArray);
        rangeTextArray = typedArray.getTextArray(R.styleable.CircleRangeView_rangeTextArray);
        Log.i(TAG,"LearnView 2 -- rangeColorArray[0]  = "+rangeColorArray[0]
        +", rangeValueArray[0] = "+rangeValueArray[0]
        +", rangeTextArray[0] = "+rangeTextArray[0]);

        borderColor = typedArray.getColor(R.styleable.CircleRangeView_borderColor, -1);
        cursorColor = typedArray.getColor(R.styleable.CircleRangeView_cursorColor, -1);
        extraTextColor = typedArray.getColor(R.styleable.CircleRangeView_extraTextColor, -1);
        Log.i(TAG,"LearnView 2 -- borderColor = "+borderColor
        +", cursorColor = "+cursorColor
        +", extraTextColor = "+extraTextColor);

        rangeTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleRangeView_rangeTextSize, -1);
        extraTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleRangeView_extraTextSize, -1);

        Log.i(TAG,"LearnView 2 -- rangeTextSize = "+rangeTextSize
                +", extraTextSize = "+extraTextSize);
        typedArray.recycle();
        mSection = 1;//等分份数
        mSparkleWidth = dp2px(15); // 指示标宽度
        mCalibrationWidth = dp2px(20);// 刻度圆弧宽度

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mRectFProgressArc = new RectF();
        mRectFCalibrationFArc = new RectF();
        mRectText = new Rect();

        mBackgroundColor = android.R.color.transparent;

    }

    private int mPadding;
    private float mLength1; // 刻度顶部相对边缘的长度
    private float mCenterX, mCenterY; // 圆心坐标
    private int mRadius; // 画布边缘半径（去除padding后的半径）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        setPadding(mPadding, mPadding, mPadding, mPadding);
        mLength1 = mPadding + mSparkleWidth ;

        int width = resolveSize(dp2px(220), widthMeasureSpec);
        mRadius = (width - mPadding * 2) / 2;
        Log.i(TAG,"mLength1 = "+mLength1+", width = "+width
                +",mRadius = "+mRadius);
        setMeasuredDimension(width, width );
        mCenterX = mCenterY = getMeasuredWidth() / 2f;
        /*mRectFProgressArc.set(
                mPadding + mSparkleWidth / 2f,
                mPadding + mSparkleWidth / 2f,
                getMeasuredWidth() - mPadding - mSparkleWidth / 2f,
                getMeasuredWidth() - mPadding - mSparkleWidth / 2f
        );*/
        mRectFProgressArc.set(
                mPadding+ mSparkleWidth,
                mPadding+ mSparkleWidth,
                getMeasuredWidth() -  mPadding - mSparkleWidth *2 ,
                getMeasuredWidth() -  mPadding - mSparkleWidth *2
        );
        float bei = 1.0f;
        mRectFCalibrationFArc.set(
                mRectFProgressArc.left+ mCalibrationWidth* bei,
                mRectFProgressArc.top + mCalibrationWidth*bei ,
                mRectFProgressArc.right- mCalibrationWidth*bei,
                mRectFProgressArc.right - mCalibrationWidth*bei
        );

        mPaint.setTextSize(sp2px(10));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);
    }


    private int extraTextSize = sp2px(14); //附加信息文本大小
    private int borderSize = dp2px(5); //进度圆弧宽度
    private int mStartAngle = 0; // 起始角度
    private int mSweepAngle = 360; // 绘制角度
    private int mCurrentAngle;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画圆弧背景
         */
        canvas.drawColor(ContextCompat.getColor(getContext(), mBackgroundColor));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderSize);
        mPaint.setAlpha(80);
        mPaint.setColor(borderColor);
        canvas.drawArc(mRectFProgressArc, mStartAngle , mSweepAngle , false, mPaint);

        /**
         * 画指示标
         */
        mPaint.setAlpha(255);
        float[] point = getCoordinatePoint(mRectFProgressArc.width()/2, mCurrentAngle);
        mPaint.setColor(cursorColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(point[0], point[1], mSparkleWidth / 2f, mPaint);

        /**
         * 画等级圆弧
         */
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAlpha(255);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(mCalibrationWidth);

        mPaint.setColor(Color.parseColor(rangeColorArray[0].toString()));
        canvas.drawArc(mRectFCalibrationFArc, mStartAngle , mSweepAngle, false, mPaint);

    }
    /**
     * 根据起始角度计算对应值应显示的角度大小
     */
    private float calculateAngleWithValue(String level) {

        int pos = -1;

        for (int j = 0; j < rangeValueArray.length; j++) {
            if (rangeValueArray[j].equals(level)) {
                pos = j;
                break;
            }
        }

        float degreePerSection = 1f * mSweepAngle / mSection;

        if (pos == -1) {
            return 0;
        } else if (pos == 0) {
            return degreePerSection / 2;
        } else {
            return pos * degreePerSection + degreePerSection / 2;
        }
    }

    /**
     * 根据角度和半径进行三角函数计算坐标
     * @param radius
     * @param angle
     * @return
     */
    private float[] getCoordinatePoint(float radius, float angle) {
        float[] point = new float[2];

        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
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
