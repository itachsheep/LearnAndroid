package com.tao.systemuidemo.selfView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tao.systemuidemo.R;

/**
 * Created by SDT14324 on 2018/3/19.
 */

public class TemperatureView extends LinearLayout {


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private int[] ivRes = {
            R.drawable.tmp_0,
            R.drawable.tmp_1,
            R.drawable.tmp_2,
            R.drawable.tmp_3,
            R.drawable.tmp_4,
            R.drawable.tmp_5,
            R.drawable.tmp_6,
            R.drawable.tmp_7,
            R.drawable.tmp_8,
            R.drawable.tmp_9,
    };
    public TemperatureView(Context context) {
        this(context,null);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    ImageView ivTemp1;
    ImageView ivTemp2;
    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_temperature,this);
        ivTemp1 = view.findViewById(R.id.iv_temp1);
        ivTemp2 = view.findViewById(R.id.iv_temp2);
    }

    public void setTemperature(int temperature){
        if(temperature >= 0 && temperature < 10){
            ivTemp2.setVisibility(View.GONE);
            ivTemp1.setImageResource(ivRes[temperature]);
        }else if(temperature >= 10 && temperature < 100){
            ivTemp2.setVisibility(View.VISIBLE);
            ivTemp1.setImageResource(ivRes[temperature/10]);
            ivTemp2.setImageResource(ivRes[temperature%10]);
        }
    }


}
