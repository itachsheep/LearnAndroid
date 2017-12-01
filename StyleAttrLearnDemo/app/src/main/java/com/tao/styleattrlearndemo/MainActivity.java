package com.tao.styleattrlearndemo;

import android.content.Context;
import android.hardware.input.InputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputManager inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);

    }
}
