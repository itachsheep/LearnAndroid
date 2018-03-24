package view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tao.viewlearn.LogUtil;

/**
 * Created by taow on 2017/8/9.
 */

@SuppressLint("AppCompatCustomView")
public class LogLinearLayout extends LinearLayout {
    public LogLinearLayout(Context context) {
        this(context,null);
    }

    public LogLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.i("LogLinearLayout.dispatchTouchEvent ");
//        return super.dispatchTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i("LogLinearLayout.onInterceptTouchEvent ");
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("LogLinearLayout.onTouchEvent ");
        return super.onTouchEvent(event);
    }
}
