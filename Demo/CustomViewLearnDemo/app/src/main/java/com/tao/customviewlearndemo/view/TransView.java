package com.tao.customviewlearndemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tao.customviewlearndemo.LogUtil;

/**
 * Created by SDT14324 on 2017/12/20.
 */

@SuppressLint("AppCompatCustomView")
public class TransView extends ImageView {
    private String TAG = "TransView";
    public TransView(Context context) {
        this(context,null);
    }

    public TransView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TransView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float a = getX() - event.getX();
        float b = getHeight()/2 - event.getY();

        float radius = getWidth();
        float rectan = (float) Math.sqrt(a*a + b*b);

        LogUtil.i(TAG,"onTounchEvent radius:  "+radius+", rectan: "+rectan+
                ", height : "+getHeight()+", width: "+getWidth());
        if(rectan > radius){
            LogUtil.i(TAG,"onTouchEvent > radius");
            return true;
        }

        return super.onTouchEvent(event);
    }
}
