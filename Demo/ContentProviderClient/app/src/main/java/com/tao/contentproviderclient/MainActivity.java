package com.tao.contentproviderclient;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btQuery;
    private Button btInsert;
    private TextView tvLog;
    private Uri uri;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btQuery = (Button) findViewById(R.id.bt_query);
        btInsert = (Button) findViewById(R.id.bt_insert);
        tvLog = (TextView) findViewById(R.id.tv_log);
        initListener();
        uri = Uri.parse("content://com.tao.server.provider");
        contentResolver = getContentResolver();
    }

    private void initListener() {
        btQuery.setOnClickListener(this);
        btInsert.setOnClickListener(this);
//        tvLog.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_query:
                contentResolver.query(uri,null,null,null,null);
                break;

            case R.id.bt_insert:

                break;
        }
    }
}
