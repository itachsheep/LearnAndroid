package com.tao.lunbodemo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Locale;

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
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        Log.i(TAG,"onCreate language = "+language);

        Log.i(TAG,"a = "+Integer.valueOf("04"));
        findViewById(R.id.all);
    }

    public void pause(View view){
        getDrawableFolderDensity();
    }

    private void getDrawableFolderDensity(){
        TypedValue typedValue = new TypedValue();
        Resources resources= getResources();
        int id = getResources().getIdentifier("a111", "drawable" , getPackageName());
        resources.openRawResource(id, typedValue);
        int density=typedValue.density;
        Log.i(TAG,"----> density="+density);
    }
}
