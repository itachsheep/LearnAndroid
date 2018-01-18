package com.tao.customviewlearndemo.nview;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by SDT14324 on 2018/1/15.
 */

public class SectorPartDrawable extends Drawable implements Drawable.Callback  {
    private String TAG =  SectorPartDrawable.class.getSimpleName();

    private Drawable mDrawable;
    private Path mPath = new Path();
    private float mPercent;

    private int mDrawType;
    private final int STYLE_LIGHT_DELAY = 1;
    private final int STYLE_PATTERN_SETTING = 2;
    private int mStyle;

    public SectorPartDrawable(Drawable drawable,int startAngle) {
        this.mDrawable = drawable;
        this.mStyle = startAngle;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | mDrawable.getChangingConfigurations();
    }

    @Override
    public boolean getPadding(Rect padding) {
        return mDrawable.getPadding(padding);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        mDrawable.setVisible(visible, restart);
        return super.setVisible(visible, restart);
    }

    private RectF mRect;
    private double mRadius;
    @Override
    public void draw(Canvas canvas) {
        mPath.reset();
        if(mRect == null){
            mRect = new RectF(getBounds());
            mRadius = Math.pow(Math.pow(mRect.right, 2) + Math.pow(mRect.bottom, 2), 0.5);
        }

        //以中间最上面点为起始点
        //drawPathCenterUp(rect, radius);
        //以中间最下面点为起始点
        //drawPathCenterDown(rect, radius);
        Log.i(TAG,"ondraw mStyle = "+mStyle);
        if(mStyle == STYLE_LIGHT_DELAY){
            //绘制照明延时灯效果，小于25.5 从90度开始，大于25.5 从 90+25.5开始
            drawLightDelay(canvas);
        }else if(mStyle == STYLE_PATTERN_SETTING) {
            //绘制模式设置效果，从45度开始画，每段角度为90，且每段不连续
            drawPatternSetting(canvas);
        }

    }

    private void drawPatternSetting(Canvas canvas) {
        mPath.moveTo(mRect.right / 2, mRect.bottom / 2);//圆心
//            if(mPercent * 360 <= 22.5 || mPercent * 360 > 337.5){// < 22.5  且 > 337.5
//
//            }

        mPath.lineTo((float) (mRect.right / 2 + mRadius * Math.cos(Math.toRadians(45))),
                (float) (mRect.bottom / 2 + mRadius * Math.sin(Math.toRadians(45))));
        mPath.lineTo(mRect.right / 2, mRect.bottom);

        if (mPercent > 0.125f) {// 大于45
            mPath.lineTo(0, mRect.bottom);
        }
        if (mPercent > 0.375f) {//大于135
            mPath.lineTo(0, 0);
        }
        if (mPercent > 0.625f) {//大于225
            mPath.lineTo(mRect.right, 0);
        }
        if (mPercent > 0.875f) {//大于315
            mPath.lineTo(mRect.right, mRect.bottom);
        }
        mPath.lineTo((float) (mRect.right / 2 - mRadius * Math.sin(Math.PI * 2 * mPercent)),
                (float) (mRect.bottom / 2 + mRadius * Math.cos(Math.PI * 2 * mPercent)));
        mPath.close();
        if (mPercent >= 0 && mPercent <= 1) {
            canvas.save();
            canvas.clipPath(mPath);
            mDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void drawLightDelay(Canvas canvas) {
        mPath.moveTo(mRect.right / 2, mRect.bottom / 2);//圆心
        if(mPercent * 360 <= 25.5){// < 25.5
            mPath.lineTo(mRect.right / 2, mRect.bottom);
        }else if(mPercent * 360 > 25.5 ) { // > 25.5
            mPath.lineTo((float) (mRect.right / 2 - mRadius * Math.sin(Math.PI * 2 * 25.5f / 360)),
                    (float) (mRect.bottom / 2 + mRadius * Math.cos(Math.PI * 2 * 25.5f / 360)));
        }

        if (mPercent > 0.125f) {// 大于45
            mPath.lineTo(0, mRect.bottom);
        }
        if (mPercent > 0.375f) {//大于135
            mPath.lineTo(0, 0);
        }
        if (mPercent > 0.625f) {//大于225
            mPath.lineTo(mRect.right, 0);
        }
        if (mPercent > 0.875f) {//大于315
            mPath.lineTo(mRect.right, mRect.bottom);
        }
        mPath.lineTo((float) (mRect.right / 2 - mRadius * Math.sin(Math.PI * 2 * mPercent)),
                (float) (mRect.bottom / 2 + mRadius * Math.cos(Math.PI * 2 * mPercent)));
        mPath.close();
        if (mPercent >= 0 && mPercent <= 1) {
            canvas.save();
            canvas.clipPath(mPath);
            mDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void drawPathCenterDown(RectF rect, double radius) {
        mPath.moveTo(rect.right / 2, rect.bottom / 2);//圆心
        mPath.lineTo(rect.right / 2, rect.bottom);//
        if (mPercent > 0.125f) {// 大于45
            mPath.lineTo(0, rect.bottom);
        }
        if (mPercent > 0.375f) {//大于135
            mPath.lineTo(0, 0);
        }
        if (mPercent > 0.625f) {//大于225
            mPath.lineTo(rect.right, 0);
        }
        if (mPercent > 0.875f) {//大于315
            mPath.lineTo(rect.right, rect.bottom);
        }
        mPath.lineTo((float) (rect.right / 2 - radius * Math.sin(Math.PI * 2 * mPercent)),
                (float) (rect.bottom / 2 + radius * Math.cos(Math.PI * 2 * mPercent)));
        mPath.close();
    }

    private void drawPathCenterUp(RectF rect, double radius) {
        mPath.moveTo(rect.right / 2, rect.bottom / 2);//圆心
        mPath.lineTo(rect.right / 2, 0);//
        if (mPercent > 0.125f) {// 大于45
            mPath.lineTo(rect.right, 0);
        }
        if (mPercent > 0.375f) {//大于135
            mPath.lineTo(rect.right, rect.bottom);
        }
        if (mPercent > 0.625f) {
            mPath.lineTo(0, rect.bottom);
        }
        if (mPercent > 0.875f) {
            mPath.lineTo(0, 0);
        }
        mPath.lineTo((float) (rect.right / 2 + radius * Math.sin(Math.PI * 2 * mPercent)),
                (float) (rect.bottom / 2 - radius * Math.cos(Math.PI * 2 * mPercent)));
        mPath.close();
    }

    @Override
    public void setAlpha(int alpha) {
        mDrawable.setAlpha(alpha);
    }

    @Override
    public int getAlpha() {
        return mDrawable.getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mDrawable.setColorFilter(cf);
    }

    @SuppressLint("NewApi")
    @Override
    public void setTintList(ColorStateList tint) {
        mDrawable.setTintList(tint);
    }

    @SuppressLint("NewApi")
    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        mDrawable.setTintMode(tintMode);
    }

    @Override
    public int getOpacity() {
        // TODO Auto-generated method stub
        return mDrawable.getOpacity();
    }

    @Override
    public boolean isStateful() {
        // TODO Auto-generated method stub
        return mDrawable.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return mDrawable.setState(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        mDrawable.setLevel(level);
        invalidateSelf();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mDrawable.setBounds(bounds);
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    /**
     * 显示的区域范围
     *
     * @param percent 0至1
     */
    public void setPercent(float percent) {
        if (percent > 1) {
            percent = 1;
        } else if (percent < 0) {
            percent = 0;
        }
        if (percent != mPercent) {
            this.mPercent = percent;
            invalidateSelf();
        }
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }
}
