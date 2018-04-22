package com.tao.systemuidemo.selfView;

import android.content.Context;
import android.content.res.TypedArray;
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

    public TemperatureView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }
    ImageView ivTemp1;
    ImageView ivTemp2;
    ImageView ivTempIcon;
    private LinearLayout llDot;
    public TemperatureView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_temperature,this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TemperatureView);
        String type = typedArray.getString(R.styleable.TemperatureView_texttype);
        if("number".equals(type)){
            ivTempIcon = (ImageView)view.findViewById(R.id.iv_temp_icon);
            ivTempIcon.setVisibility(View.GONE);
        }
        ivTemp1 = (ImageView)view.findViewById(R.id.iv_temp1);
        ivTemp2 = (ImageView)view.findViewById(R.id.iv_temp2);
        llDot = (LinearLayout)view.findViewById(R.id.ll_dot);
    }

    public void setNumber(int number){
        //
        //tmp : 0 - 9
        if(number >= 0 && number < 10){
            ivTemp2.setVisibility(View.GONE);
            ivTemp1.setImageResource(ivRes[number]);
        }else if(number >= 10 && number < 100){
            //tmp : 10 - 100
            ivTemp2.setVisibility(View.VISIBLE);
            ivTemp1.setImageResource(ivRes[number/10]);
            ivTemp2.setImageResource(ivRes[number%10]);
        }
    }

    public void setTemp(int temperature){
        //判断是否是 0.5显示
        if(temperature % 10 == 0){
            llDot.setVisibility(View.GONE);
        }else {
            llDot.setVisibility(View.VISIBLE);
        }
        temperature = temperature / 10;
        //tmp : 0 - 9
        if(temperature >= 0 && temperature < 10){
            ivTemp2.setVisibility(View.GONE);
            ivTemp1.setImageResource(ivRes[temperature]);
        }else if(temperature >= 10 && temperature < 100){
            //tmp : 10 - 100
            ivTemp2.setVisibility(View.VISIBLE);
            ivTemp1.setImageResource(ivRes[temperature/10]);
            ivTemp2.setImageResource(ivRes[temperature%10]);
        }
    }



}
