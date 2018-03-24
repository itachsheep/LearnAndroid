package com.tao.customviewlearndemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.skyworthauto.aircondition.views.ExSeekBar;
import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.view.SectorView;


public class SectorViewActivity extends AppCompatActivity implements View.OnTouchListener {
    private String TAG = "SectorViewActivity";
    private ExSeekBar seekCool;
    private static final int MAX_WIN = 7;

    private SectorView sectorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectorview);
        sectorView = (SectorView) findViewById(R.id.sectorView);
        init();
    }

    private void init() {
        sectorView.setEnabled(true);
        sectorView.setOnTouchListener(this);
        sectorView.setViewChangeListenner(new SectorView.CircleViewChangeListen() {
            @Override
            public void viewChange(int position, boolean onresume) {

            }
        });
        sectorView.setACMode(1);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        sectorView.onTouchEvent(event);
        return true;
    }
}
