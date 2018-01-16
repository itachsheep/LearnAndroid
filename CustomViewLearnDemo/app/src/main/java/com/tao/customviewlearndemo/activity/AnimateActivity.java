package com.tao.customviewlearndemo.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tao.customviewlearndemo.R;

/**
 * Created by SDT14324 on 2018/1/16.
 */

public class AnimateActivity extends AppCompatActivity {

    private ImageView animationIV;
    private Button buttonA, buttonB, buttonC;
    private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);


        animationIV = (ImageView) findViewById(R.id.animationIV);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);

        buttonA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                animationIV.setImageResource(R.drawable.animation1);
                animationDrawable = (AnimationDrawable) animationIV.getDrawable();
                animationDrawable.start();
            }

        });

        buttonB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                animationDrawable = (AnimationDrawable) animationIV.getDrawable();
                animationDrawable.stop();
            }

        });

        buttonC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                animationIV.setImageResource(R.drawable.animation2);
                animationDrawable = (AnimationDrawable) animationIV.getDrawable();
                animationDrawable.start();
            }
        });

    }

    public void start(View view){
        /*ObjectAnimator oa = ObjectAnimator.ofFloat(imageView, "rotationY",
                new float[] { 90f, 180f, 270f, 360f });
// 一个周期时长
        oa.setDuration(3000);
//         重复次数
        oa.setRepeatCount(ObjectAnimator.INFINITE);
//         重复方式
        oa.setRepeatMode(ObjectAnimator.REVERSE);
        oa.start();*/
       /* _3DAnimation animation = new _3DAnimation(0,180);
        animation.setDuration(15000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(animation);*/
    }

}
