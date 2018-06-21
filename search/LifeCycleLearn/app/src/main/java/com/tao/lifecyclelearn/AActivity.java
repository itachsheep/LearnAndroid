package com.tao.lifecyclelearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AActivity extends AppCompatActivity {
    private String TAG = AActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG,"onCreate");
        setContentView(R.layout.activity_a);
    }

    public void startb(View view){
        startActivity(new Intent(AActivity.this,BActivity.class));

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
