package com.tao.styleattrlearndemo;

import android.content.Context;
import android.hardware.input.InputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputManager inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {


        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            throw new Exception("printStack");
        }catch (Exception e){
            Log.d(TAG,"dispatchTouchEvent--- "+e.getLocalizedMessage(),e);
        }
        return super.dispatchTouchEvent(ev);
    }
}
