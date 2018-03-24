package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import util.LogUtil;

/**
 * Created by taow on 2017/5/31.
 */

public class AutoTextView extends TextView {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mBackgroundRect;
    /** 字幕内容 */
    private String mText;

    /** 字幕字体颜色 */
    private int mTextColor;

    /** 字幕字体大小 */
    private float mTextSize;

    private Rect mRect = new Rect();

    private float offX = 0f;

    private float mStep = 2.0f;

    private int FRESHTIME = 30;

    public AutoTextView(Context context) {
        this(context,null);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setSingleLine(true);
//        mBackgroundPaint.setStyle(Paint.Style.STROKE);
//        mBackgroundPaint.setS
        mBackgroundPaint.setColor(0xff000011);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mText = getText().toString();
        mTextColor = getCurrentTextColor();
        mTextSize = getTextSize();

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText,0,mText.length(),mRect);

        LogUtil.i("AutoTextView.onMeasure mTextColor:"+mTextColor+", mTextSize:"+mTextSize+
        ", mText:"+mText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x,y;
//        x = getMeasuredWidth() - offX;
//        y = ((getHeight() - mRect.height()) >> 1 ) + mRect.height() - 10;
//        LogUtil.i("AutoTextView.onDraw measuredWidth: "+getMeasuredWidth()+
//        ", offX: "+offX+", getHeight: "+getHeight()+", mRect.height: "+mRect.height());
//        LogUtil.i("AutoTextView.onDraw x:"+x+", y: "+y);

        x = getWidth() - 10 - offX;
        y = ((getHeight() - mRect.height()) >> 1 ) + mRect.height() - 10;
        LogUtil.i("AutoTextView.onDraw x:"+x+", y: "+y);
        if (null == mBackgroundRect) {
            mBackgroundRect = new Rect(10, 8, getWidth()-10 , getHeight()-10);
        }
        setTextColor(Color.TRANSPARENT);
        canvas.drawRect(mBackgroundRect, mBackgroundPaint);
        canvas.drawText(mText,x,y,mPaint);
        offX += mStep;
        if(offX >= getMeasuredWidth() + mRect.width()){
            offX = 0;
        }
    }
    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            LogUtil.i("AutoTextView.refreshRunnable time: "+ SystemClock.currentThreadTimeMillis());
            postDelayed(refreshRunnable,FRESHTIME);
        }
    };

    @Override
    public void setVisibility(int visibility) {
        if(visibility == View.VISIBLE){
            offX = 0;
        }else {
            removeCallbacks(refreshRunnable);
        }
        super.setVisibility(visibility);

        if(visibility == View.VISIBLE){
            postDelayed(refreshRunnable,FRESHTIME);
        }

    }
}
