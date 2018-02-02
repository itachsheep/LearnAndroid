package com.tao.contextdemo;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context applicationContext = getApplicationContext();
        Context baseContext = getBaseContext();
        Application application = getApplication();
        LogUtil.i(TAG,"onCreate applicationContext = "+applicationContext
                +", baseContext = "+baseContext);

        Toast.makeText(getBaseContext(),"111111",Toast.LENGTH_LONG).show();
    }
}
