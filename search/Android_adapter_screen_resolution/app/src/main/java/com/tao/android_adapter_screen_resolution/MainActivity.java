package com.tao.android_adapter_screen_resolution;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getBaseContext().getResources();
        XmlResourceParser xmlResourceParser = resources.getLayout(R.layout.activity_main);
        Log.i(TAG,"context = "+getBaseContext()
        +", resource = "+resources+", xmlResourceParser = "+xmlResourceParser);
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method m = cls.getDeclaredMethod("get",String.class,String.class);
            String res = (String) m.invoke(null,"net.hostname","");
            Log.i(TAG,"res = "+res);
        } catch (Exception e) {
            Log.i(TAG, "E = " + e.getMessage());
            e.printStackTrace();
        }
        String re  = Build.SERIAL;
    }
}
