package com.tao.wei.testjni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ShellHolderView mShellHolderView;
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShellHolderView = new ShellHolderView(MainActivity.this);
        mResult = findViewById(R.id.tv_result);
    }

    public void onNativeAddInt(View view){
        mResult.setText("1 + 2 = "+mShellHolderView.nativeAdd(1, 2));
    }

    public void onNativeAddFloat(View view){
        mResult.setText("0.1 + 0.2 = "+mShellHolderView.nativeAdd(0.1f,0.2f));
    }
}
