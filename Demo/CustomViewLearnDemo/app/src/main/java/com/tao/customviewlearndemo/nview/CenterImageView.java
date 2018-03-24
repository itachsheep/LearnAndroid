package com.tao.customviewlearndemo.nview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by SDT14324 on 2018/1/16.
 */

@SuppressLint("AppCompatCustomView")
public class CenterImageView extends ImageView {
    private String TAG = "CenterImageView";
    private float mRadius;
//    private float mInnerRadius ;
    private float mCenterX ;
    private float mCenterY;
    private OnInnerListener mListener;
    private float mResponseRadius;

    public CenterImageView(Context context) {
        super(context);
    }

    public CenterImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRadius = Math.max(height,width) * 1f / 2;
//        mInnerRadius = mRadius - dp2px(30);
        mCenterX = mRadius;
        mCenterY = mRadius;
        mResponseRadius = mRadius - dp2px(15);
        Log.i(TAG,"onMeasure height = "+height+", width = "+width);
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG,"dispatchTouchEvent ");
        return super.dispatchTouchEvent(event);
    }*/

    public interface OnInnerListener{
        void onInnerClick(MotionEvent event);
    }

    public void setInnerListener(OnInnerListener listener){
        mListener = listener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent ");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                float x = event.getX();
                float y = event.getY();
                double r = Math.pow((Math.pow(x - mCenterX, 2) + Math.pow(y - mCenterY, 2)), 0.5);
                Log.i(TAG,"r = "+r+", mResponseRadius = "+mResponseRadius);
                if(r < mResponseRadius ){
                    if(mListener != null){
                        mListener.onInnerClick(event);
                    }
                    return true;
                }
                break;
            }

        }
        return super.onTouchEvent(event);
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
