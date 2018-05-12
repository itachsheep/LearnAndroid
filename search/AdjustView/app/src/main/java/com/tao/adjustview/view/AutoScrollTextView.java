package com.tao.adjustview.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.tao.adjustview.R;

/**
 * Created by SDT14324 on 2018/5/12.
 */

public class AutoScrollTextView extends LinearLayout {
    private String TAG = AutoScrollTextView.class.getSimpleName();
    private MyTextView myTextView;
    private ScrollView scrollView;
    private Handler mhandler = new Handler();


    public AutoScrollTextView(Context context) {
        this(context,null);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_textview,this);
        init(view);
    }

    private void init(View view) {
        myTextView = (MyTextView) view.findViewById(R.id.test_marquee);
        scrollView = (ScrollView) view.findViewById(R.id.test_scrollview);
    }

    public void setTtext(String ttext){
        myTextView.setPadding(8, 6, 8, 6);
        myTextView.setLineSpacing(28, 1.0f);
        myTextView.setDBCText(ttext);
        startDownRunnable();
    }

    public void startDownRunnable(){
        mhandler.postDelayed(downRunnable,500);
    }

    private int mScrollDis = 0;
    private Runnable downRunnable = new Runnable() {
        @Override
        public void run() {
            int maxScrollDis = myTextView.getHeight() - myTextView.getLineHeight() * 3;
            mScrollDis += 2;
            if(mScrollDis > maxScrollDis){
                mhandler.removeCallbacks(downRunnable);
            }else {
                scrollView.scrollTo(0,mScrollDis);
                mhandler.postDelayed(downRunnable,80);
            }

        }
    };
}
