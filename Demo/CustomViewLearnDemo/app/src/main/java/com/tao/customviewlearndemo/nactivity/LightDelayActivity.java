package com.tao.customviewlearndemo.nactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.LightDelayView;

/**
 * Created by SDT14324 on 2018/1/18.
 */

public class LightDelayActivity extends AppCompatActivity {
    LightDelayView lightDelayView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_delay);
        lightDelayView = findViewById(R.id.spr_root);
        lightDelayView.init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lightDelayView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
