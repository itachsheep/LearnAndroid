package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.tao.customviewlearndemo.R;

/**
 * Created by taowei on 2018/1/14.
 * 2018-01-14 22:33
 * CustomViewLearnDemo
 * com.tao.customviewlearndemo.view
 */

public class ClipDrawView extends View {
    private String TAG = "ClipDrawView";
//    private BitmapDrawable mDestPic;
//    private BitmapDrawable mBgPic;

    private Bitmap mDestBitmap;
    private Bitmap mBgBitmap;
    private int mLayoutSize;

    private Rect mSrcRect;
    private Rect mDestRect;
    private Rect mBgRect;

    private Paint mPaint;

    public ClipDrawView(Context context) {
        this(context,null);
    }

    public ClipDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClipDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDestBitmap =  ((BitmapDrawable)context.getResources()
                .getDrawable(R.drawable.ctest)).getBitmap();
        mBgBitmap = ((BitmapDrawable)context.getResources()
                .getDrawable(R.drawable.ctest_bg)).getBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mLayoutSize = Math.min(width,height);

        mBgRect = new Rect(0,0,mLayoutSize,mLayoutSize);
        mSrcRect = new Rect(0,0,mLayoutSize,mLayoutSize);
        mDestRect = new Rect(0,0,mLayoutSize,mLayoutSize);

        mPaint = new Paint();

        Log.i(TAG,"width = "+width
                +",height = "+height
                +"onMeasure mLayoutSize = "+mLayoutSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         //绘制背景
        /*canvas.save();
        canvas.drawBitmap(mBgBitmap,mBgRect,mBgRect,mPaint);
        canvas.restore();*/

        //
       /* canvas.save();
        canvas.clipRect(mDestRect);
        canvas.drawBitmap(mDestBitmap,10,10,mPaint);
        canvas.restore();*/

        //绘制背景
        /*canvas.save();
        Path path = new Path();
        path.addCircle(0,0,mLayoutSize, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(mBgBitmap,0,0,mPaint);
        canvas.restore();*/

        //绘制图片
        /*canvas.save();
        canvas.drawBitmap(mDestBitmap,mSrcRect,mDestRect,mPaint);
        canvas.restore();*/

        canvas.save();
        Path path = new Path();
        // 添加一个圆形区域
//        path.addCircle(130, 130, mLayoutSize/5, Path.Direction.CW);
        path.addArc(new RectF(0,0,mLayoutSize,mLayoutSize),0,270);
        // 也可以通过设置 path 来显示自定义区域
        canvas.clipPath(path);
//        canvas.drawBitmap(mDestBitmap, 0, 0, mPaint);
        canvas.drawColor(Color.RED);
        canvas.drawBitmap(mDestBitmap,mSrcRect,mDestRect,mPaint);
        canvas.restore();
    }

    private int dp2px(int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
