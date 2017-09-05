package navi_.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.skyworth.navi.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    protected String[] needPermissions = new String[]
            {"android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.READ_PHONE_STATE"
            };
    private void checkPermissions(String... var1) {
        if(Build.VERSION.SDK_INT >= 23 && this.getApplicationInfo().targetSdkVersion >= 23) {
            List var2 = this.findDeniedPermissions(var1);
            if(null != var2 && var2.size() > 0) {
                try {
                    String[] var3 = (String[])var2.toArray(new String[var2.size()]);
                    Method var4 = this.getClass().getMethod("requestPermissions", new Class[]{String[].class, Integer.TYPE});
                    var4.invoke(this, new Object[]{var3, Integer.valueOf(0)});
                } catch (Throwable var5) {
                    ;
                }
            }
        }
    }
    private List<String> findDeniedPermissions(String[] var1) {
        ArrayList var2 = new ArrayList();
        if(Build.VERSION.SDK_INT >= 23 && this.getApplicationInfo().targetSdkVersion >= 23) {
            String[] var3 = var1;
            int var4 = var1.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String var6 = var3[var5];
                if(this.checkMySelfPermission(var6) != 0 || this.shouldShowMyRequestPermissionRationale(var6)) {
                    var2.add(var6);
                }
            }
        }

        return var2;
    }
    private int checkMySelfPermission(String var1) {
        try {
            Method var2 = this.getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer var3 = (Integer)var2.invoke(this, new Object[]{var1});
            return var3.intValue();
        } catch (Throwable var4) {
            return -1;
        }
    }
    private boolean shouldShowMyRequestPermissionRationale(String var1) {
        try {
            Method var2 = this.getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean var3 = (Boolean)var2.invoke(this, new Object[]{var1});
            return var3.booleanValue();
        } catch (Throwable var4) {
            return false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions(this.needPermissions);
    }

}
