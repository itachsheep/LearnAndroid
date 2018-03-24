package com.tao.customviewlearndemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by SDT14324 on 2017/12/19.
 */

public class ParabolaView extends View {


    public ParabolaView(Context context) {
        this(context,null);
    }

    public ParabolaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    private Paint mPaint;
    private Path path;
    public ParabolaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(25);

        path = new Path();

    }

    float x1,x2,y1,y2,m ;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(100,100,50,mPaint);


        mPaint.setColor(Color.BLACK);
        canvas.drawText("welcom to my world!!",50,50,mPaint);
        x1 = 0;
        y1 = 0;
        while(m < 200){
            synchronized (ParabolaView.this) {
                m = m + 0.1f;
                x2 = m + 1;
                y2 = 0.0145f * x2 * x2;
                canvas.drawLine(x1, y1, x2, y2, mPaint);
                x1 = x2;
                y1 = y2;
            }
        }

//        canvas.drawLine(0,0,100,100,mPaint);
//        canvas.drawLine(100,100,100,400,mPaint);
    }
}
