package com.tao.lunbodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by SDT14324 on 2018/4/14.
 */

public class HttpActivity extends AppCompatActivity {
    private String TAG  = HttpActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.i(TAG,"density = "+displayMetrics.density
        +", densityDpi = "+displayMetrics.densityDpi
        +", widthPixels = "+displayMetrics.widthPixels
        +", heightPixels = "+displayMetrics.heightPixels
        );



    }
}
