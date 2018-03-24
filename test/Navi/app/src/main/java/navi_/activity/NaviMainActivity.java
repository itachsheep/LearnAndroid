package navi_.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.skyworth.navi.R;

import navi_.customui.CustomUiActivity;

public class NaviMainActivity extends Activity {
    private AMapNaviView aMapNaviView;
    private AMapNavi mAMapNavi;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        /*// 获取AMapNaviView实例，并设置监听。
        aMapNaviView = (AMapNaviView) findViewById(R.id.main_navimap);
        aMapNaviView.onCreate(savedInstanceState);
        aMapNaviView.setAMapNaviViewListener(aMapNaviViewListener);
        //获取AMapNavi实例，并设置监听
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(aMapNaviListener);*/

    }



    private String[] examples = new String[]

            {"导航组件(新)",
                    "驾车路线规划",
                    "步行路线规划",
                    "骑行路线规划",
                    "实时导航",
                    "模拟导航",
                    "智能巡航",
                    "传入GPS数据导航",
                    "导航UI自定义",
                    "HUD导航",
                    "导航回调说明(见代码)",
                    "展示导航路径详情(见代码)"
            };

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//组件
                startActivity(new Intent(NaviMainActivity.this, ComponentActivity.class));
            } else if (position == 1) {//驾车
                startActivity(new Intent(NaviMainActivity.this, DriverListActivity.class));
            } else if (position == 2) {//步行路线规划
//                startActivity(new Intent(NaviMainActivity.this, WalkRouteCalculateActivity.class));
            } else if (position == 3) {//骑行路线规划
//                startActivity(new Intent(NaviMainActivity.this, RideRouteCalculateActivity.class));
            } else if (position == 4) {//实时导航
                startActivity(new Intent(NaviMainActivity.this, GPSNaviActivity.class));
            } else if (position == 5) {//模拟导航
//                startActivity(new Intent(NaviMainActivity.this, EmulatorActivity.class));
            } else if (position == 6) {//只能巡航
//                startActivity(new Intent(NaviMainActivity.this, IntelligentBroadcastActivity.class));
            } else if (position == 7) {//使用设备外GPS数据导航
//                startActivity(new Intent(NaviMainActivity.this, UseExtraGpsDataActivity.class));
            } else if (position == 8) {//导航UI在自定义
                startActivity(new Intent(NaviMainActivity.this, CustomUiActivity.class));
            } else if (position == 9) {//HUD导航
//                startActivity(new Intent(NaviMainActivity.this, HudDisplayActivity.class));
            } else if (position == 10) {//导航数据(回调)
//                startActivity(new Intent(NaviMainActivity.this, NaviInfoActivity.class));
            } else if (position == 11) {//展示导航路径详情
//                startActivity(new Intent(NaviMainActivity.this, GetNaviStepsAndLinksActivity.class));
            }
        }
    };

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                NaviMainActivity.this,
                android.R.layout.simple_list_item_1,
                examples);
        listView.setAdapter(adapter);
        setTitle(" xxxxxxxxxxxxxx sdk - "+AMapNavi.getVersion());
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






    @Override
    protected void onResume() {
        super.onResume();

    }

}
