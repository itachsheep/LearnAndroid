package com.tao.lifecyclelearn.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tao.lifecyclelearn.LogUtil;
import com.tao.lifecyclelearn.R;

public class AActivity extends AppCompatActivity {
    private String TAG = AActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG,"onCreate");
        setContentView(R.layout.activity_a);
//        Bitmap.createScaledBitmap()
        /*BitmapFactory.decodeFile();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inScaled = true;
        options.inDensity = srcWidth;
        options.inTargetDensity = dstWidth;
        options.inSampleSize = 2;
        File file;
        file.listFiles();
        file.list();*/
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        LogUtil.i(TAG,"activityManager.getMemoryClass()  = "+activityManager.getMemoryClass() );
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
