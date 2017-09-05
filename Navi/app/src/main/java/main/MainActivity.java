package main;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skyworth.navi.R;

import map_.activity.MapMainActivity;
import navi_.activity.NaviMainActivity;

/**
 * Created by SDT14324 on 2017/9/5.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private String[] examples = new String[]{
            "地图sdk",
            "导航sdk",
            "定位sdk",};

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//map
                startActivity(new Intent(MainActivity.this, MapMainActivity.class));
            } else if (position == 1) {//navi
                startActivity(new Intent(MainActivity.this, NaviMainActivity.class));
            } else if (position == 2) {//locate
//                startActivity(new Intent(main.MainActivity.this, WalkRouteCalculateActivity.class));
            }
        }
    };

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                examples);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mItemClickListener);
    }

    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }
}
