package com.tao.customviewlearndemo.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/14.
 */

public class PPCircleProgressView extends View {
    private String TAG = "PPCircleProgressView";

    private float progress = 0; //显示的进度
    private String strprogress = "100"; //显示的进度
    private int mLayoutSize = 100;//整个控件的尺寸（方形）
    public int mColor;//主要颜色
    public int mColorBackground;

    Context mContext;

    private float now = 0; //当前的进度

    public PPCircleProgressView(Context context) {
        super(context);
        mContext = context;
    }

    public PPCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mColor = context.getResources().getColor(R.color.yellow);
        mColorBackground = context.getResources().getColor(R.color.colorPrimary);
    }

    public PPCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        mLayoutSize = Math.min(widthSpecSize, heightSpecSize);
        if (mLayoutSize == 0) {
            mLayoutSize = Math.max(widthSpecSize, heightSpecSize);
        }

        setMeasuredDimension(mLayoutSize, mLayoutSize);
    }

    private int dip2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0x66d4b801);
        paint.setStyle(Paint.Style.FILL); //设置空心

        //画灰线
        int centre = getWidth() / 2; //获取圆心的x坐标
        float radius = centre * 0.96f; //圆环的半径
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        paint.setColor(mColorBackground);
        canvas.drawCircle(centre, centre, radius - dip2px(1f), paint); //画出圆环


        float gap = dip2px(14);
        RectF rectF = new RectF(gap, gap, mLayoutSize - gap, mLayoutSize - gap);

        //15度一个格子，防止占半个格子
        int endR = (int) (360 * (now / 100) / 15) * 15;
        paint.setColor(0x44d4b801);
        canvas.drawArc(rectF, 0, 360, true, paint);

        paint.setColor(mColor);
        canvas.drawArc(rectF, -90, endR, true, paint);

        //画红圆
        paint.setColor(mColorBackground);
        paint.setStyle(Paint.Style.FILL); //设置空心
        radius = radius * 0.83f; //圆环的半径
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        //画线，许多的线，15度画一条，线的宽度是2dp
        paint.setStrokeWidth(dip2px(2));

        for (int r = 0; r < 360; r = r + 15) {
            canvas.drawLine(centre + (float) ((centre - gap) * Math.sin(Math.toRadians(r))),
                    centre - (float) ((centre - gap) * Math.cos(Math.toRadians(r))),
                    centre - (float) ((centre - gap) * Math.sin(Math.toRadians(r))),
                    centre + (float) ((centre - gap) * Math.cos(Math.toRadians(r))), paint);
        }

        paint.setColor(0x44d4b801);
        canvas.drawCircle(centre, centre, radius - dip2px(2f), paint); //画出圆环
        paint.setColor(mColorBackground);
        canvas.drawCircle(centre, centre, radius - dip2px( 2.5f), paint); //画出圆环

        //到此，背景绘制完毕

        String per = (int) now + "";

        //写百分比
        if ("".equals(strprogress)) {
            paint.setColor(mColor);
            paint.setStrokeWidth(dip2px(2));
            canvas.drawLine(centre * 0.77f, centre, centre * 0.95f, centre, paint);
            canvas.drawLine(centre * 1.05f, centre, centre * 1.23f, centre, paint);
        } else {
            paint.setColor(mColor);
            paint.setTextSize(mLayoutSize / 4f);//控制文字大小
            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setTextSize(mLayoutSize / 12);//控制文字大小
            paint2.setColor(mColor);
            canvas.drawText(per,
                    centre - 0.5f * (paint.measureText(per)),
                    centre - 0.5f * (paint.ascent() + paint.descent()),
                    paint);
            canvas.drawText("分",
                    centre + 0.5f * (paint.measureText((int) now + "") + paint2.measureText("分")),
                    centre - 0.05f * (paint.ascent() + paint.descent()),
                    paint2);
        }

        //外部小球
        centre = getWidth() / 2;
        canvas.drawCircle(centre + (float) ((centre * 0.95f) * Math.sin(Math.toRadians(endR))),
                centre - (float) ((centre * 0.95f) * Math.cos(Math.toRadians(endR))), centre * 0.04f, paint);

        Path p = new Path();
        p.moveTo(centre + (float) ((centre * 0.86f) * Math.sin(Math.toRadians(endR))),
                centre - (float) ((centre * 0.86f) * Math.cos(Math.toRadians(endR))));

        p.lineTo(centre + (float) ((centre * 0.94f) * Math.sin(Math.toRadians(endR + 2.5))),
                centre - (float) ((centre * 0.94f) * Math.cos(Math.toRadians(endR + 2.5))));
        p.lineTo(centre + (float) ((centre * 0.94f) * Math.sin(Math.toRadians(endR - 2.5))),
                centre - (float) ((centre * 0.94f) * Math.cos(Math.toRadians(endR - 2.5))));
        p.close();
        canvas.drawPath(p, paint);

        if (now < progress - 1) {
            now = now + 1;
            postInvalidate();
        } else if (now < progress) {
            now = (int) progress;
            postInvalidate();
        }
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

}
