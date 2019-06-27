package com.tao.animatesearch;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;

public class AnimateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimatedVectorDrawable animatedVectorDrawable;
        FlingAnimation flingAnimation;
        SpringAnimation springAnimation;
        Transition transition;
        Scene scene;
    }
}
