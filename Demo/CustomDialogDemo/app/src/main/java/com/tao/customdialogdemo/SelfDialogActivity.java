package com.tao.customdialogdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tao.dialog.AdjustDialog;


/**
 * Created by taow on 2017/6/19.
 */

public class SelfDialogActivity extends Activity implements View.OnClickListener,
        AdjustDialog.OnDialogItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Button bt = (Button) findViewById(R.id.dialog_bt);
        bt.setOnClickListener(this);

        //EditText et = (EditText) findViewById(R.id.dialog_et);


    }

    @Override
    public void onClick(View v) {
        AdjustDialog dialog = new AdjustDialog(this, R.layout.view_dialog,new int[]{});

        dialog.show();
        dialog.setTextView(R.id.dialog_tip_text,"PIN码修改成功！");
        dialog.setOnDialogItemClickListener(this);
    }

    @Override
    public void OnDialogItemClick(AdjustDialog dialog, View itemView) {

    }
}
