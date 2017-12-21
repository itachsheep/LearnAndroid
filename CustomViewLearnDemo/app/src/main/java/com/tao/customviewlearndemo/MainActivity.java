package com.tao.customviewlearndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skyworthauto.aircondition.views.ExSeekBar;

public class MainActivity extends AppCompatActivity  {
    private String TAG = "MainActivity";
    private ExSeekBar seekCool;
    private static final int MAX_WIN = 7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"ts onclick !!!");
            }
        });
    }


}
