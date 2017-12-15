package com.tao.customviewlearndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.skyworthauto.aircondition.views.ExSeekBar;

public class MainActivity extends AppCompatActivity  {
    private ExSeekBar seekCool;
    private static final int MAX_WIN = 7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_ac);

//        seekCool = findViewById(R.id.seekCool);
//        seekCool.setMax(MAX_WIN);
//        seekCool.setProgress(3);
//        seekCool.setEnabled(true);
//        seekCool.setOnExSeekBarChangeListener(this);
    }


}
