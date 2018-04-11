package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tao.lunbodemo.R;

/**
 * Created by SDT14324 on 2018/4/10.
 */

public class VolumeView extends View {

    private Paint mPaint ;
    private Drawable mVolumeBg;
    private Drawable mVolumeNor;
    private int mVolueWidth;
    private int mVolueHeight;
    private String TAG = "VolumeView";
    private int progress;
    private int max;
    private int space;
    private int topMargin;
    private int bgWidth;
    private int bgHeight;


    private float moveX;
    private float upX;
    private float downX;
    private int tempLeft;
    public VolumeView(Context context) {
        this(context,null);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mVolumeBg = getResources().getDrawable(R.mipmap.vol_bg);
        mVolumeNor = getResources().getDrawable(R.mipmap.vol_nor);
        mVolueWidth = mVolumeBg.getIntrinsicWidth();
        mVolueHeight = mVolumeBg.getIntrinsicHeight();
        Log.i(TAG,"mVolueWidth = "+mVolueWidth+", mVolueHeight = "+mVolueHeight);

        topMargin = 10;
        space = 15;
        max = 37;
        progress = 23;
        bgWidth = max * (mVolueWidth + space)+space ;
        bgHeight = topMargin * 2 + mVolueHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bgWidth,bgHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0,0,bgWidth,bgHeight,mPaint);
        for(int i = 0 ; i < max; i++){
            tempLeft = space + i * (mVolueWidth + space);
            mVolumeBg.setBounds(tempLeft, topMargin, mVolueWidth +tempLeft, mVolueHeight+topMargin);
            mVolumeBg.draw(canvas);
        }
        for (int j = 0; j < progress; j++){
            tempLeft = space + j * (mVolueWidth + space);
            mVolumeNor.setBounds(tempLeft, topMargin, mVolueWidth +tempLeft, mVolueHeight+topMargin);
            mVolumeNor.draw(canvas);
        }
    }


    public void setProgress(int pb){
        if(pb > max) {
            pb = max;
        }else if(pb < 0){
            pb = 0;
        }
        progress = pb;
        invalidate();
    }

    public void setMax(int maxVol){
        max = maxVol;
        invalidate();
    }

    public void addProgress(){
        progress++;
        if(progress > max) {
            progress = max;
        }
        invalidate();
    }

    public void subProgress(){
        progress--;
       if(progress < 0){
            progress = 0;
       }
       invalidate();
    }

    public int getProgress(){
        return progress;
    }

    public int getMax(){
        return max;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {

//                downX = event.getX();
//                progress = (int) (downX / getWidth() * max);
//                Log.i(TAG,"downX = "+downX+", width = "+getWidth()+", pb = "+progress);
//                invalidate();
            }
                break;
            case MotionEvent.ACTION_MOVE:
            {
                moveX = event.getX();
                progress = (int) (moveX / getWidth() * max);
                Log.i(TAG,"movex = "+moveX);
                invalidate();
            }
                break;
            case MotionEvent.ACTION_UP:
            {
                upX = event.getX();
                progress = (int) (upX / getWidth() * max);
                invalidate();
            }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            {
                Log.i(TAG,"ACTION_CANCEL  ACTION_OUTSIDE");
            }
                break;
        }
        return super.onTouchEvent(event);
    }
}
