package com.tao.customviewlearndemo.nactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.SwitchView;

/**
 * Created by SDT14324 on 2018/1/18.
 */

public class SwitchActivity extends AppCompatActivity {
    private SwitchView switchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        switchView = findViewById(R.id.switch_org);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switchView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
