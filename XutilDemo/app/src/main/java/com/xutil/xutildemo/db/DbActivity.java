package com.xutil.xutildemo.db;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xutil.xutildemo.App;
import com.xutil.xutildemo.R;
import com.xutil.xutildemo.download.DownloadActivity;

import java.util.List;

public class DbActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btWrite;
    private Button btLastTask;
    private Button btFindAll;
    private Button btDownload;
    private Button btUpdateDb;
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
        btUpdateDb = (Button) findViewById(R.id.ac_bt_updateDb);

        btWrite.setOnClickListener(this);
        btLastTask.setOnClickListener(this);
        btFindAll.setOnClickListener(this);
        btUpdateDb.setOnClickListener(this);
    }

    private int state = 1, process = 1;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ac_bt_write:
                Student student = new Student();

                student.set_id(10);
                student.setName("lili");
                student.setAge(20);

                UpgradeTask task = new UpgradeTask();
                task.setId(1);
                task.setState(20);
                task.setDownloadProcess(100);


                App.getInstance().getStuManager().saveObj(student);
                App.getInstance().getStuManager().saveObj(task);
                tvContent.setText("写入： "+student.toString());

                break;
            case R.id.ac_bt_lastTask:
                Student stu = App.getInstance().getStuManager().obtainLastStudent();
                UpgradeTask task2 = App.getInstance().getStuManager().obtainLastUpgradeTask();

                tvContent.setText("最新stu： "+stu+" \n"+", upgrade: "+task2);
                break;

            case R.id.ac_bt_findALl:
                List<Student> list = App.getInstance().getStuManager().findAll();
                tvContent.setText("全部： "+list.toString());
                break;
            case R.id.ac_bt_gotodownload:
                startActivity(new Intent(DbActivity.this, DownloadActivity.class));
                break;

            case R.id.ac_bt_updateDb:
                App.getInstance().getStuManager().updateDb();
//                App.getInstance().getStuManager().addColumn();
                break;
        }
    }
}
