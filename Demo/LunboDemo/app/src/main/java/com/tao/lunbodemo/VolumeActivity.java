package com.tao.lunbodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import view.VolumeLayout;

/**
 * Created by SDT14324 on 2018/4/10.
 */

public class VolumeActivity extends AppCompatActivity {
    private VolumeLayout mVl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);
        mVl = findViewById(R.id.vl);
    }

    public void addVolume(View view){
        mVl.addVolume();
    }

    public void subVolume(View view){
        mVl.subVolume();
    }
}
