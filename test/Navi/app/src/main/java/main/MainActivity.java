package main;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skyworth.navi.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import map_.activity.MapMainActivity;
import navi_.activity.NaviMainActivity;
import rocker.MapRockerActivity;
import rocker.RockerActivity;
import skyworth.activity.SkyworthActivity;

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
            "定位sdk",
            "模拟摇杆",
            "地图摇杆",
            "创维导航"
            };

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//map
                startActivity(new Intent(MainActivity.this, MapMainActivity.class));
            } else if (position == 1) {//navi
                startActivity(new Intent(MainActivity.this, NaviMainActivity.class));
            } else if (position == 2) {//locate
//                startActivity(new Intent(main.MainActivity.this, WalkRouteCalculateActivity.class));
            }else if(position == 3){
                startActivity(new Intent(MainActivity.this, RockerActivity.class));
            }else if(position == 4){
                startActivity(new Intent(MainActivity.this, MapRockerActivity.class));
            }else if(position == 5){
                startActivity(new Intent(MainActivity.this, SkyworthActivity.class));
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
