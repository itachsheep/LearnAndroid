package com.tao.statemachinedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static com.tao.statemachinedemo.Hsm1.makeHsm1;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Object().notify();

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onclick!!");
                Hsm1 hsm = makeHsm1();
                synchronized (MainActivity.this) {
                    hsm.sendMessage(hsm.obtainMessage(hsm.CMD_1));
//                    hsm.sendMessage(hsm.obtainMessage(hsm.CMD_2));
//                    try {
//                        // wait for the messages to be handled
//
//                        hsm.wait();
//                    } catch (InterruptedException e) {
//                        Log.e(TAG, "exception while waiting " + e.getMessage());
//                    }
                }
            }
        });

    }
}
