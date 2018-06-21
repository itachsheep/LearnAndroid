package com.tao.lifecyclelearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BActivity extends AppCompatActivity {
    private String TAG = BActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG,"onCreate");
        setContentView(R.layout.activity_b);

    }
    @Override
    protected void onStart() {
        LogUtil.i(TAG,"onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        LogUtil.i(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtil.i(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtil.i(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtil.i(TAG,"onDestroy");
        super.onDestroy();
    }

}
