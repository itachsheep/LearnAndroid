package com.amap.navi.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.navi.AMapNavi;
import com.amap.navi.demo.activity.custom.ComponentActivity;
import com.amap.navi.demo.activity.custom.CustomUiActivity;
import com.amap.navi.demo.util.CheckPermissionsActivity;
import com.zyxnet.autonavi.R;

import java.util.Arrays;

/**
 * Created by shixin on 16/8/23.
 * bug反馈QQ:1438734562
 */
public class IndexActivity extends CheckPermissionsActivity {

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//组件
                startActivity(new Intent(IndexActivity.this, ComponentActivity.class));
            } else if (position == 1) {//驾车
                startActivity(new Intent(IndexActivity.this, DriverListActivity.class));
            } else if (position == 2) {//步行路线规划
                startActivity(new Intent(IndexActivity.this, WalkRouteCalculateActivity.class));
            } else if (position == 3) {//骑行路线规划
                startActivity(new Intent(IndexActivity.this, RideRouteCalculateActivity.class));
            } else if (position == 4) {//实时导航
                startActivity(new Intent(IndexActivity.this, GPSNaviActivity.class));
            } else if (position == 5) {//模拟导航
                startActivity(new Intent(IndexActivity.this, EmulatorActivity.class));
            } else if (position == 6) {//只能巡航
                startActivity(new Intent(IndexActivity.this, IntelligentBroadcastActivity.class));
            } else if (position == 7) {//使用设备外GPS数据导航
                startActivity(new Intent(IndexActivity.this, UseExtraGpsDataActivity.class));
            } else if (position == 8) {//导航UI在自定义
                startActivity(new Intent(IndexActivity.this, CustomUiActivity.class));
            } else if (position == 9) {//HUD导航
                startActivity(new Intent(IndexActivity.this, HudDisplayActivity.class));
            } else if (position == 10) {//导航数据(回调)
                startActivity(new Intent(IndexActivity.this, NaviInfoActivity.class));
            } else if (position == 11) {//展示导航路径详情
                startActivity(new Intent(IndexActivity.this, GetNaviStepsAndLinksActivity.class));
            }



        }
    };
    private String[] examples = new String[]

            {"导航组件<font color='red'>(新)<font>", "驾车路线规划", "步行路线规划", "骑行路线规划", "实时导航", "模拟导航", "智能巡航", "传入GPS数据导航",
                    "导航UI自定义", "HUD导航", "导航回调说明(见代码)", "展示导航路径详情(见代码)"
            };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        IndexAdapter adapter = new IndexAdapter(this, Arrays.asList(examples));
        listView.setAdapter(adapter);
        setTitle("导航SDK " + AMapNavi.getVersion());
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
