package com.tao.lunbodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
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

        getFilesDir();
        Log.i("test","dir = "+getFilesDir());
    }

    public void addVolume(View view){
        mVl.addVolume();
        Log.i("VolumeActivity","addVolume");
        SparseArray<int[]> array = new SparseArray<>();
        int[] arr = new int[10];
        int a = arr.length;
        array.put(0,new int[]{1,2,3,85, 4, 11, 1, 0, 3, 13,85, 4, 11, 1, 0, 3, 13});
    }

    public void subVolume(View view){
        mVl.subVolume();
    }
}
