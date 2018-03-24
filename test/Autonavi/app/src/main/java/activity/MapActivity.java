package activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.zyxnet.autonavi.BuildConfig;
import com.zyxnet.autonavi.R;

import utils.MyCheckPermissionsActivity;

public class MapActivity extends MyCheckPermissionsActivity implements View.OnClickListener {
    private MapView mapView;
    private Button btSwitch;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private boolean isNeedCheck = Build.VERSION.SDK_INT < 23 ? false : true;

    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initView();

        mapView.onCreate(savedInstanceState);
        initMap();
        initListener();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_bt_switch:

                break;

        }
    }

    private void initMap() {
        //1,show the bitmap


        //2, get bitmap manager class
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        //3, show blue location point
        showBlueLocation();

        //4, offline manager map load
        //构造OfflineMapManager对象
        OfflineMapManager amapManager = new OfflineMapManager(getApplicationContext(), mapDownloadListener);
        //按照citycode下载
//        amapManager.downloadByCityCode(String citycode)；
//        //按照cityname下载
//        amapManager.downloadByCityName(String cityname)；

    }

    private void showBlueLocation() {
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）
        // 如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        myLocationStyle.strokeColor(Color.RED);
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);

    }

    private OfflineMapManager.OfflineMapDownloadListener mapDownloadListener = new OfflineMapManager.OfflineMapDownloadListener() {
        @Override
        public void onDownload(int i, int i1, String s) {

        }

        @Override
        public void onCheckUpdate(boolean b, String s) {

        }

        @Override
        public void onRemove(boolean b, String s, String s1) {

        }
    };


    private void initView() {
        mapView = (MapView) findViewById(R.id.main_map);
        btSwitch = (Button) findViewById(R.id.main_bt_switch);
    }

    private void initListener() {
        btSwitch.setOnClickListener(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
