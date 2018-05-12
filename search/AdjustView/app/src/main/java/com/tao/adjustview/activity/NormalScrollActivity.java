package com.tao.adjustview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

import com.tao.adjustview.R;

/**
 * Created by SDT14324 on 2018/5/12.
 */

public class NormalScrollActivity extends AppCompatActivity {
    ScrollView scrollView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_scroll);
        scrollView = findViewById(R.id.normal_scrollview);
    }

    public void startDown(View view){
//        scrollView.smoothScrollTo(0,300);
//        scrollView.scrollTo(0,300);

        scrollView.scrollBy(0,300);
    }
}
