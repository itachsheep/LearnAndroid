package com.tao.customviewlearndemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.customviewlearndemo.R;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSectorView(View view){
        startActivity(new Intent(MainActivity.this, SectorViewActivity.class));
    }

    public void toCirleRangeView(View view){
        startActivity(new Intent(MainActivity.this, CircleRangeActivity.class));
    }

    public void toNormalCircle(View view){
        startActivity(new Intent(MainActivity.this,NormalCircleActivity.class));
    }

    public void toAnimate(View view ){
        startActivity(new Intent(MainActivity.this,AnimateActivity.class));
    }
}
