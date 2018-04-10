package com.tao.systemuidemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tao.systemuidemo.R;
import com.tao.systemuidemo.selfView.TemperatureView;

/**
 * Created by SDT14324 on 2018/3/14.
 */

public class PanelActivity extends AppCompatActivity implements View.OnClickListener {
    TemperatureView temperatureView;
    Button btTempUp;
    Button btTempDown;
    int temp = 30;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_panel);

        temperatureView = findViewById(R.id.tempView);
        btTempUp = findViewById(R.id.bt_temp_up);
        btTempDown = findViewById(R.id.bt_temp_down);
        btTempUp.setOnClickListener(this);
        btTempDown.setOnClickListener(this);
        temperatureView.setTemperature(temp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_temp_up:
            {
                temp++;
                temperatureView.setTemperature(temp);
            }
                break;
            case R.id.bt_temp_down:
            {
                temp--;
                temperatureView.setTemperature(temp);
            }
            break;
        }

    }
}
