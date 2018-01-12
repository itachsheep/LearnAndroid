package com.tao.customviewlearndemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.view.AirView;
import com.tao.customviewlearndemo.view.CircleRangeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SDT14324 on 2018/1/11.
 */

public class CircleRangeActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleRangeView circleRangeView;
    private AirView mAirView;
    private String [] valueArray;
    private Random random;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicle_range);
        circleRangeView=  findViewById(R.id.circleRangeView);
        mAirView = findViewById(R.id.menu_airview);
        circleRangeView.setOnClickListener(this);

        valueArray=getResources().getStringArray(R.array.circlerangeview_values);
        random=new Random();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circleRangeView:

                List<String> extras =new ArrayList<>();
                extras.add("收缩压：116");
                extras.add("舒张压：85");

                int i=random.nextInt(valueArray.length);
                circleRangeView.setValueWithAnim(valueArray[i],extras);

                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mAirView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
