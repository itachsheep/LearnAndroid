package com.tao.customviewlearndemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.SwitchView;

/**
 * Created by SDT14324 on 2018/1/17.
 */

public class C30Activity2 extends AppCompatActivity {
    private SwitchView switchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c30_2);
        switchView = findViewById(R.id.switch_org);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switchView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
