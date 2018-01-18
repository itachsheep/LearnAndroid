package com.tao.customviewlearndemo.nactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.PatternView;

/**
 * Created by SDT14324 on 2018/1/18.
 */

public class PatternActivity extends AppCompatActivity {
    private PatternView patternView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        patternView = findViewById(R.id.pv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        patternView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
