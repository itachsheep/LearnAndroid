package com.tao.customviewlearndemo.nactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.AtmosphereLightView;

/**
 * Created by SDT14324 on 2018/1/18.
 */

public class AtmosActivity extends AppCompatActivity {
    private AtmosphereLightView atmosphereLightView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atomsphere);
        atmosphereLightView = findViewById(R.id.atmos_alv);
        atmosphereLightView.init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        atmosphereLightView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
