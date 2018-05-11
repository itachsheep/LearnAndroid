package com.tao.adjustview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SDT14324 on 2017/10/24.
 */

@SuppressLint("AppCompatCustomView")
public class SkyVerticalMarqueeTextview extends TextView {
    private String TAG = "SkyVertical";
    private final int MGS_START_MARQUEE = 0x01;
    private final int MGS_STOP_MARQUEE = 0x02;

    public SkyVerticalMarqueeTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setFocusable(true);
    }

    public SkyVerticalMarqueeTextview(Context context) {
        super(context);
        this.setFocusable(true);
    }

    private int maxScrollY;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case MGS_START_MARQUEE:
                    startMarquee();
                    break;
                case MGS_STOP_MARQUEE:
                    setMarqueeModel(MarqueeModel.MANUAL);
                    break;
                default:
                    break;
            }
        }
    };

    private void removeAllMessage(){
        mHandler.removeMessages(MGS_START_MARQUEE);
        mHandler.removeMessages(MGS_STOP_MARQUEE);
    }

    private void startMarqueeDelay(){
        removeAllMessage();
        mHandler.sendEmptyMessageDelayed(MGS_START_MARQUEE, 8000);
    }


    private void startMarquee(){
        setMarqueeModel(MarqueeModel.AUTO_ON_VISIBLE);
    }

    private void stopMarquee(){
        removeAllMessage();
        mHandler.sendEmptyMessage(MGS_STOP_MARQUEE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,getLineCount() * getLineHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initMaxScrollAndCheckFrezon();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                stopMarquee();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                stopMarquee();

                break;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                startMarqueeDelay();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                startMarqueeDelay();
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void checkEnoughToMarquee() {
        Log.i(TAG,"checkEnoughToMarquee maxScrollY = "+maxScrollY+", height = "+getHeight());
        isEnoughToMarquee = maxScrollY > getHeight();
    }

    private boolean isFrozenFromWindowFocusChanged = Boolean.FALSE;
    private boolean isFrozenFromVisible = Boolean.FALSE;
    private boolean hasAttachedToWindow;
    private boolean isEnoughToMarquee = false;

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            Log.i(TAG,"onWindowFocusChanged hasWindowFocus: "+hasWindowFocus
            +", isFrozenFromWindowFocusChanged : "+isFrozenFromWindowFocusChanged);
            if (isFrozenFromWindowFocusChanged) {
                isFrozenFromWindowFocusChanged = false;
                checkFrozen();
            }
        } else {
            isFrozen = true;
            isFrozenFromWindowFocusChanged = true;
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.i(TAG,"onWindowFocusChanged visibility = "+visibility);
        if (visibility == View.VISIBLE) {
            if (isFrozenFromVisible) {
                isFrozenFromVisible = false;
                checkFrozen();
            }
        } else {
            isFrozen = true;
            isFrozenFromVisible = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        hasAttachedToWindow = true;
        checkFrozen();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hasAttachedToWindow = false;
        checkFrozen();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        Log.i(TAG,"setText type = "+type);
        initMaxScrollAndCheckFrezon();
    }

    private void checkFrozen() {
        boolean oldIsFronze = isFrozen;
        isFrozen = !(isEnoughToMarquee && hasAttachedToWindow
                && !isFrozenFromVisible && !isFrozenFromWindowFocusChanged);
        Log.i(TAG,"checkFrozen oldIsFronze = "+oldIsFronze+", isFrozen = "+isFrozen);
        if (oldIsFronze) {
            Log.i(TAG,"checkFrozen marqueeModel = "+marqueeModel+". isMarquee = "+isMarquee);
            if (marqueeModel == MarqueeModel.AUTO_ON_VISIBLE) {
                if (isMarquee) {
                    checkScroll();
                } else {
                    doStartMarquee();
                }
            } else if (isMarquee) {
                checkScroll();
            }
        }
    }

    private final int FPS = 1000 / 25;
    private final int SPEED = 1;
    private boolean isFrozen = true;

    private void checkScroll() {
        if (isMarquee && !isFrozen) {
            //postDelayed(scrollTextRunnable, FPS);
        }
    }

    private void initMaxScrollAndCheckFrezon() {
        post(makeMaxScrollAndCheckFrezon);
    }

    final Runnable makeMaxScrollAndCheckFrezon = new Runnable() {

        @Override
        public void run() {

            maxScrollY = getLineCount() * getLineHeight();
            Log.i(TAG,"makeMaxScrollAndCheckFrezon lineCount = "+getLineCount()+", maxScrollY = "+maxScrollY);
            checkEnoughToMarquee();
            checkFrozen();
        }
    };
    final Runnable scrollTextRunnable = new Runnable() {
        @Override
        public void run() {
            if (isMarquee && !isFrozen) {
                scrollBy(0, SPEED);
                int scrollY = getScrollY();
                Log.i(TAG,"scrollY = "+scrollY+", stopY = "+(maxScrollY-4*getLineHeight())+", maxScrollY = "+maxScrollY);
                if (scrollY > maxScrollY-4*getLineHeight()) {
                    removeAllMessage();
                    doStopMarquee();
                    // scrollTo(0, -getHeight());
                }
                checkScroll();
            }
        }
    };

    private boolean isMarquee = false;

    public void doStartMarquee() {
        if (isMarquee) {
            return;
        }
        isMarquee = true;
        checkScroll();
    }

    /*public void resetMarqueeLocation() {
        scrollTo(0, 0);
    }*/

    public void doStopMarquee() {
        isMarquee = false;
        removeCallbacks(scrollTextRunnable);
    }

    public static int STATE_MARQUEE_RUNNING = 1;
    public static int STATE_MARQUEE_STOPED = 2;
    public static int STATE_FROZEN = 3;
    public static int STATE_NO_ENOUGH_LENGTH = 4;

    /*public int getMarqueeState() {
        if (isFrozen) {
            return STATE_FROZEN;
        }
        if (!isEnoughToMarquee) {
            return STATE_NO_ENOUGH_LENGTH;
        }
        if (isMarquee) {
            return STATE_MARQUEE_RUNNING;
        } else {
            return STATE_MARQUEE_STOPED;
        }
    }*/

    private MarqueeModel marqueeModel = MarqueeModel.AUTO_ON_VISIBLE;

    public void setMarqueeModel(MarqueeModel model) {
        marqueeModel = model;
        if (marqueeModel == null) {
            marqueeModel = MarqueeModel.AUTO_ON_VISIBLE;
        }
        switch (marqueeModel) {
            case AUTO_ON_VISIBLE:
                if (getVisibility() == View.VISIBLE) {
                    doStartMarquee();
                } else {
                    doStopMarquee();
                }
                break;
            case MANUAL:
                doStopMarquee();
                break;
        }
    }

    public enum MarqueeModel {
        AUTO_ON_VISIBLE,
        MANUAL
    }
    public void setDBCText(String text) {
        Log.i(TAG,"setDBCText ");
        if(text != null){
            String mTextDBC = changToDBC(text);
            setText(mTextDBC);
        }
    }
    private String changToDBC(String text) {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
