package com.xutil.xutildemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xutil.xutildemo.download.DownloadActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btWrite;
    private Button btLastTask;
    private Button btFindAll;
    private Button btDownload;
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btDownload = (Button) findViewById(R.id.ac_bt_gotodownload);
        btWrite = (Button) findViewById(R.id.ac_bt_write);
        btLastTask = (Button) findViewById(R.id.ac_bt_lastTask);
        btFindAll = (Button) findViewById(R.id.ac_bt_findALl);
        tvContent = (TextView) findViewById(R.id.tv_content);

        btWrite.setOnClickListener(this);
        btLastTask.setOnClickListener(this);
        btFindAll.setOnClickListener(this);
    }

    private int state = 1, process = 1;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ac_bt_write:
                UpgradeTask task = new UpgradeTask();
//                task.setState(state++);
//                task.setDownloadProcess(process++);
                task.setId(1);
                task.setState(100);
                task.setDownloadProcess(100);
//                TaskManager.getTaskManager().saveObj(task);
                TaskManager.getTaskManager().updateObj(task);
                tvContent.setText("写入： "+task.toString());

                break;
            case R.id.ac_bt_lastTask:
                UpgradeTask lastTask = TaskManager.getTaskManager().obtainLastTask();
                tvContent.setText("最新： "+lastTask.toString());
                break;
            case R.id.ac_bt_findALl:
                List<UpgradeTask> list = TaskManager.getTaskManager().findAll();
                tvContent.setText("全部： "+list.toString());
                break;
            case R.id.ac_bt_gotodownload:
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                break;
        }
    }
}
