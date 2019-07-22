package com.tao.wei.hybirdflutter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taobao.idlefish.flutterboost.FlutterBoostPlugin;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import hybird1.Hybird1NativeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static WeakReference<MainActivity> sRef;

    private TextView mOpenNative;
    private TextView mOpenFlutter;
    private TextView mOpenFlutterFragment;
    private TextView mOpenMyFlutter;
    private TextView mOpenFirst;
    private TextView mHybirdNoBoost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sRef = new WeakReference<>(this);

        setContentView(R.layout.main_activity);

        mOpenNative = findViewById(R.id.open_native);
        mOpenFlutter = findViewById(R.id.open_flutter);
        mOpenFlutterFragment = findViewById(R.id.open_flutter_fragment);
        mOpenMyFlutter = findViewById(R.id.open_my_flutter);
        mOpenFirst = findViewById(R.id.open_first);
        mHybirdNoBoost = findViewById(R.id.hybird_no_boost);

        mOpenNative.setOnClickListener(this);
        mOpenFlutter.setOnClickListener(this);
        mOpenFlutterFragment.setOnClickListener(this);
        mOpenMyFlutter.setOnClickListener(this);
        mOpenFirst.setOnClickListener(this);
        mHybirdNoBoost.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sRef.clear();
        sRef = null;
    }

    @Override
    public void onClick(View v) {
        if (v == mOpenNative) {
            PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL);
        } else if (v == mOpenFlutter) {
            PageRouter.openPageByUrl(this, PageRouter.FLUTTER_PAGE_URL);
           // FlutterBoostPlugin.onPageResult("result_id_100",new HashMap(),new HashMap());
        } else if (v == mOpenFlutterFragment) {
            PageRouter.openPageByUrl(this, PageRouter.FLUTTER_FRAGMENT_PAGE_URL);
        }else if(v == mOpenMyFlutter){
            PageRouter.openPageByUrl(this,PageRouter.FLUTTER_MY_PAGE_URL);
        }else if(v == mOpenFirst){
            PageRouter.openPageByUrl(this,PageRouter.FLUTTER_FIRST);
        }else if(v == mHybirdNoBoost) {
            startActivity(new Intent(this, Hybird1NativeActivity.class));
        }
    }
}
