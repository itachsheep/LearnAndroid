package com.tao.lunbodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import view.CustomDialog;

/**
 * Created by SDT14324 on 2018/4/10.
 */

public class ProgressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb);

    }

    public void showDial(View view){
        /*final ProgressDialog pd = new ProgressDialog(ProgressActivity.this);
//        pd.setTitle(getText(com.android.internal.R.string.power_off));
        pd.setTitle("关机");
//        pd.setMessage(getText(com.android.internal.R.string.shutdown_progress));
        pd.setMessage("正在关机。。。");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
//        pd.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);

        pd.show();*/

        CustomDialog dialog = new CustomDialog(ProgressActivity.this);
        dialog.show();
        dialog.setCancelable(false);

    }
}
