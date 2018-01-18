package com.tao.customviewlearndemo.nactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.ThemeView;

/**
 * Created by SDT14324 on 2018/1/18.
 */

public class ThemeViewActivity extends AppCompatActivity {
    private ThemeView themeView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        themeView = findViewById(R.id.theme_tv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        themeView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
