package com.xutil.xutildemo.contentprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xutil.xutildemo.LogUtil;
import com.xutil.xutildemo.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by SDT14324 on 2017/10/11.
 */


public class ContentActivity extends Activity {

    @ViewInject(R.id.ac_ct_tv)
    private TextView tvShow;


//    @Event(R.id.ac_ct_get)
//    private void onGetClick(View view){
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Button bt = (Button) findViewById(R.id.ac_ct_get);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("onClick ...get content ");
                ContentResolver contentResolver = getContentResolver();
                ContentValues values = new ContentValues();
                values.put("key","test_key_02");
                values.put("value","test_value_02");
                contentResolver.insert(Uri.parse("content://com.skyworthdigital.upgrade/deviceinfo"),values);
            }
        });
    }
}
