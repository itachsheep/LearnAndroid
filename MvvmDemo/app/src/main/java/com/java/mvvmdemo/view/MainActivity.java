package com.java.mvvmdemo.view;

import android.os.Bundle;

import com.java.mvvmdemo.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
