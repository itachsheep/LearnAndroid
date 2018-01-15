package com.tao.customviewlearndemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.nview.NormalCircleView;
import com.tao.customviewlearndemo.view.ClipDrawView;
import com.tao.customviewlearndemo.view.SectorDrawable;

/**
 * Created by SDT14324 on 2018/1/15.
 */

public class NormalCircleActivity extends AppCompatActivity {
    private NormalCircleView normalCircleView;
//    private ImageView mIvSector;
    private SectorDrawable sectorDrawable;
    private ClipDrawView clipDrawView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_circle);

        //normalCircleView = findViewById(R.id.nc_view);
//        mIvSector = findViewById(R.id.iv_sector);
//        sectorDrawable = new SectorDrawable(mIvSector.getDrawable());
//        mIvSector.setImageDrawable(sectorDrawable);

        clipDrawView = findViewById(R.id.cd_drawable);
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        private float percent;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (percent <= 1) {
                percent += 0.01;
            } else {
                percent = 0;
                return;
            }
            sectorDrawable.setPercent(percent);
            Log.i("this",String.valueOf(percent));
            sendEmptyMessageDelayed(0, 10);
        }
    };

    public void test(View view){
        mHandler.sendEmptyMessage(0);
//        sectorDrawable.setPercent(0.1f);
        clipDrawView.setPercent(0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //normalCircleView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
