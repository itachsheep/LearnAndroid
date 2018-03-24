package com.skyworthdigital.upgrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.utils.LogUtil;

public class MainActivity extends Activity {
    private Button bt;
    private TextView tv;
    private Handler handler;
    private SRunnable sRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_main);
        bt = (Button) findViewById(R.id.ac_bt);
        tv = (TextView) findViewById(R.id.ac_tv);

        handler = new Handler();
        sRunnable = new SRunnable();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("onclick ...");
                MainActivity.this.sendBroadcast(new Intent("com.skyworth.test.br"));
//                handler.post(sRunnable);
            }
        });



    }

    class SRunnable implements Runnable {

        @Override
        public void run() {
            UpgradeTask upgradeTask = UpgradeApp.getInstance().getTaskManager().obtainLastTask();
            if(upgradeTask != null)
                tv.setText("upgradeTask ： "+upgradeTask.toString());
            LogUtil.i(" upgradeTask : "+upgradeTask);
            handler.postDelayed(this,5000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sRunnable);
    }
}
