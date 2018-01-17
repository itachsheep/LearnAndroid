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
import com.tao.customviewlearndemo.nview.AtmosphereLightView;
import com.tao.customviewlearndemo.nview.NormalCircleView;
import com.tao.customviewlearndemo.nview.ClipDrawView;
import com.tao.customviewlearndemo.nview.SectorEntireDrawable;
import com.tao.customviewlearndemo.nview.LightDelayView;

/**
 * Created by SDT14324 on 2018/1/15.
 */

public class C30Activity extends AppCompatActivity {
    private NormalCircleView normalCircleView;
//    private ImageView mIvSector;
    private SectorEntireDrawable sectorEntireDrawable;
    private ClipDrawView clipDrawView;
    private LightDelayView lightDelayView;
    private AtmosphereLightView atmosphereLightView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_circle);

        //normalCircleView = findViewById(R.id.nc_view);
//        mIvSector = findViewById(R.id.iv_sector);
//        sectorEntireDrawable = new SectorEntireDrawable(mIvSector.getDrawable());
//        mIvSector.setImageDrawable(sectorEntireDrawable);

        clipDrawView = findViewById(R.id.cd_drawable);
        lightDelayView = findViewById(R.id.spr_root);
        atmosphereLightView = findViewById(R.id.atmos_alv);
    }

    public void freshSectorPartRoot(View view){
        lightDelayView.hideClipPartView();
    }

    public void fresAtmosphere(View view){
        atmosphereLightView.hideOutPart();
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
            sectorEntireDrawable.setPercent(percent);
            Log.i("this",String.valueOf(percent));
            sendEmptyMessageDelayed(0, 10);
        }
    };

    public void test(View view){
        mHandler.sendEmptyMessage(0);
//        sectorEntireDrawable.setPercent(0.1f);
        clipDrawView.setPercent(0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //normalCircleView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
