package com.tao.usecase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tao.usecase.R;

/**
 * Created by SDT14324 on 2017/9/14.
 */

public class VisibilityActivity extends Activity {
    private Button bt1;
    private Button bt2;
    private Button bt3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visib);
        bt1 = (Button) findViewById(R.id.visi_bt1);
        bt2 = (Button) findViewById(R.id.visi_bt2);
        bt3 = (Button) findViewById(R.id.visi_bt3);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bt2.getVisibility() == View.VISIBLE){
                    bt2.setVisibility(View.INVISIBLE);
                }else {
                    bt2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
