package com.tao.customviewlearndemo.nview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/16.
 */

public class SectorPartRoot extends RelativeLayout implements CenterImageView.OnInnerListener, ClipPartView.OnProgressListener {
    private String TAG = "SectorPartRoot";

    private float mRadius;
    private float mInnerRadius;
    private float mCenterX ;
    private float mCenterY;

    private ClipPartView clipPartView;
    private CenterImageView centerImageView;
    private TextView tvNum;
    private int mode = 0;
    public SectorPartRoot(Context context) {
        this(context,null);
    }

    public SectorPartRoot(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectorPartRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_setor_holder,this);
        clipPartView = view.findViewById(R.id.spr_cpv);
        centerImageView = view.findViewById(R.id.spr_center_iv);
        tvNum = view.findViewById(R.id.spr_tv_num);
        initListener();
        clipPartView.setProgrss(30f/360);
    }

    private void initListener() {
        centerImageView.setInnerListener(this);
        clipPartView.setProgressListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRadius = Math.max(height,width) * 1f / 2;
        mInnerRadius = mRadius - dp2px(30);
        mCenterX = mRadius;
        mCenterY = mRadius;
        Log.i(TAG,"onMeasure height = "+height+", width = "+width);
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,"dispatchTouchEvent ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG,"onInterceptTouchEvent ");
        return super.onInterceptTouchEvent(ev);
    }*/

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent ");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                float x = event.getX();
                float y = event.getY();
                double r = Math.pow((Math.pow(x - mCenterX, 2) + Math.pow(y - mCenterY, 2)), 0.5);
                Log.i(TAG,"r = "+r+", mInnerRadius = "+mInnerRadius);
                if(r < mInnerRadius){
                    showOrHide();
                   return true;
                }
                break;
            }

        }
        return super.onTouchEvent(event);
    }*/

    private void showClipPartView(){
        if (clipPartView.getVisibility() == View.INVISIBLE){
            clipPartView.setVisibility(View.VISIBLE);
        }

    }
    public void hideClipPartView(){
        if(clipPartView.getVisibility() == View.VISIBLE){
            clipPartView.setVisibility(View.INVISIBLE);
        }
        // 当ClipPartView不可见时，显示进度条
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void onInnerClick(MotionEvent event) {
        showClipPartView();
    }

    @Override
    public void onProgressChange(float angle) {
        //1，更新数值
        updateTvNum(angle);
        //2，角度变换
    }

    private void updateTvNum(float angle) {
        if(angle >0  && angle <= 30){
            mode = 0;
        }else if(angle > 30 && angle <= 90){
            mode = 15;
        }else if(angle > 90 && angle <= 150){
            mode = 30;
        }else if(angle > 150 && angle <= 240){
            mode = 45;
        }else if(angle > 240 && angle <= 360){
            mode = 60;
        }
        tvNum.setText(mode + "s");
    }
}
