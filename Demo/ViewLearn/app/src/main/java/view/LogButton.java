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
public class LogButton extends Button {
    public LogButton(Context context) {
        this(context,null);
    }

    public LogButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.i("LogButton.dispatchTouchEvent ");
        return super.dispatchTouchEvent(event);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("LogButton.onTouchEvent ");
        return super.onTouchEvent(event);
    }
}
