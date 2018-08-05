package com.tao.lottiesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        TextView textView;
        LottieAnimationView animationOriginView = (LottieAnimationView) findViewById(R.id.animation_origin_view);
//        animationView.setAnimation("hello-world.json");
        animationOriginView.loop(true);
        animationOriginView.playAnimation();


        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//        animationView.setAnimation("hello-world.json");
        animationView.loop(true);
        animationView.playAnimation();

    }
}
