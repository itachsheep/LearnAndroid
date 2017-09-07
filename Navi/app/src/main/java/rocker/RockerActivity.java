package rocker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.gcssloop.widget.RockerView;
import com.skyworth.navi.R;

import java.util.ArrayList;
import java.util.List;

import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/6.
 */

public class RockerActivity extends Activity implements AMapNaviListener {
//    private com.gcssloop.widget.RockerView
    private RockerView mRockerView;
    private AMapNaviView mAmapNaviView;
    private AMap mAmap;
    private AMapNavi mAmapNavi;
    private List<NaviLatLng> slist = new ArrayList<>();
    private List<NaviLatLng> elist = new ArrayList<>();
    private List<NaviLatLng> wlist = new ArrayList<>();

    private NaviLatLng mStartLatLng = new NaviLatLng(39.825934,116.342972);
    private NaviLatLng mEndLatLng = new NaviLatLng(40.084894,116.603039);
    private Object lockObj = new Object();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocker);
        mRockerView = (RockerView) findViewById(R.id.rocker);
        mAmapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAmap = mAmapNaviView.getMap();
        mAmapNaviView.onCreate(savedInstanceState);

        //AMapNaviViewOptions viewOptions = mAmapNaviView.getViewOptions();

        //init AmapNavi
        mAmapNavi = AMapNavi.getInstance(getApplicationContext());
        mAmapNavi.addAMapNaviListener(this);
        //use extra gps data
        mAmapNavi.setIsUseExtraGPSData(true);
        //set car speed
        mAmapNavi.setEmulatorNaviSpeed(75);
        slist.add(mStartLatLng);
        elist.add(mEndLatLng);

        initListener();
    }

    @Override
    public void onInitNaviSuccess() {
        //caculate route
        LogUtils.i("RockerActivity.onInitNaviSuccess");
        if(isFirst){
            int strategyConvert = mAmapNavi.strategyConvert(false, false, false, false, false);
            mAmapNavi.calculateDriveRoute(slist,elist,wlist,strategyConvert);
        }
    }

    private boolean isFirst = true;
    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //show route or start navi
        LogUtils.i("RockerActivity.onCalculateRouteSuccess");
        if(isFirst){
            mAmapNavi.startNavi(NaviType.GPS);
            isFirst = false;
            /*synchronized (RockerActivity.this){
                LogUtils.i("onCalculateRouteSuccess gps isOpen: "+isGPSEnable());
                toggleGPS();
                LogUtils.i("onCalculateRouteSuccess gps isOpen: "+isGPSEnable());
            }*/
        }
    }

    //accurate parameters
    private double lng = 0.00001;
    private double lat = 0.00001;
    private double        x ;
    private double        y ;
    private float  accuracy =  100.0f;
    private float     speed =  5.0f;
    private double startLat =  39.825934;
    private double startLng = 116.342972;
    private double    multi =   0.0000001;
//    private Location location = new Location("gps");

    private double radian;

    private void initListener() {
        mRockerView.setListener(new RockerView.RockerListener() {
            @Override
            public void callback(int eventType, int angle, float currentDistance) {
                switch (eventType) {
                    case RockerView.EVENT_ACTION:

                        if(angle == -1){
                            return;
                        }
                        synchronized (lockObj){
                            radian = Math.toRadians(angle);
                            y = currentDistance * Math.sin(radian);
                            x = currentDistance * Math.cos(radian);

                            LogUtils.i("### y "+y+", x: "+x);
                            // add longtitude,East West
                            lng = x * multi;
                            //add latitude,North South
                            lat = y * multi;
                            //East West
                            startLng = startLng + lng;
                            //North South
                            startLat = startLat + lat;
                            Location location = new Location("gps");
                            location.setLongitude(startLng);
                            location.setLatitude(startLat);
                            location.setSpeed(speed);
                            location.setAccuracy(accuracy);
                            location.setBearing(5);
                            location.setTime(System.currentTimeMillis());
                            //use gaode pivot = 2, use system pivot = 1

                            mAmapNavi.setExtraGPSData(2,location);

                        }

                        break;
                   /* case RockerView.EVENT_CLOCK:
                        // 定时回调
                        Log.e("EVENT_CLOCK", " angle : "+currentAngle+" - distance"+currentDistance);
                        break;*/
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAmapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAmapNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAmapNaviView.onDestroy();
    }

    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try{
            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
        }catch(PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


    //判断gps是否处于打开状态
    public boolean isOpen() {
        if (Build.VERSION.SDK_INT <19) {
            LocationManager myLocationManager = (LocationManager  )getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            return myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }else{
            int state = Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
            if(state==Settings.Secure.LOCATION_MODE_OFF){
                return false;
            }else{
                return true;
            }
        }
    }

    private boolean isGPSEnable() {
        /*
        用Setting.System来读取也可以，只是这是更旧的用法
        Stringstr = Settings.System.getString(getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        */
        String str = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        LogUtils.i("GPS : "+ str);
        if(str != null) {
            return str.contains("gps");
        } else{
            return false;
        }
    }

    /************************************************************************
     * **********************************************************************
     */
    @Override
    public void onInitNaviFailure() {
        LogUtils.i("RockerActivity.onInitNaviFailure");
        Toast.makeText(this, "init navi_.navi Failed", Toast.LENGTH_SHORT).show();
    }
    

    @Override
    public void onStartNavi(int i) {
        LogUtils.i("RockerActivity.onStartNavi");
//开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        LogUtils.i("RockerActivity.onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        //当前位置回调
        LogUtils.i("RockerActivity.onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        LogUtils.i("RockerActivity.onGetNavigationText type: "+type+", text: "+text);
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtils.i("RockerActivity.onGetNavigationText s: "+s);
    }

    @Override
    public void onEndEmulatorNavi() {
//结束模拟导航
        LogUtils.i("RockerActivity.onEndEmulatorNavi ");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        LogUtils.i("RockerActivity.onArriveDestination ");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        LogUtils.i("RockerActivity.onCalculateRouteFailure");

    }

    @Override
    public void onReCalculateRouteForYaw() {
//偏航后重新计算路线回调
        LogUtils.i("RockerActivity.onReCalculateRouteForYaw ");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        LogUtils.i("RockerActivity.onReCalculateRouteForTrafficJam  ");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
//到达途径点
        LogUtils.i("RockerActivity.onArrivedWayPoint s: ");
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        //GPS开关状态回调
        LogUtils.i("RockerActivity.onGpsOpenStatus  ");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        LogUtils.i("RockerActivity.onNaviInfoUpdate ");
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
        //过时
        LogUtils.i("RockerActivity.onNaviInfoUpdated");
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
        LogUtils.i("RockerActivity.updateCameraInfo ");
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
        LogUtils.i("RockerActivity.onServiceAreaUpdate ");
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
        LogUtils.i("RockerActivity.showCross ");
    }

    @Override
    public void hideCross() {
//隐藏转弯回调
        LogUtils.i("RockerActivity.hideCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        //显示车道信息
        LogUtils.i("RockerActivity.showLaneInfo");
    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        LogUtils.i("RockerActivity.hideLaneInfo");
    }
    

    @Override
    public void notifyParallelRoad(int i) {
        LogUtils.i("RockerActivity.notifyParallelRoad ");
        
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //更新交通设施信息
        LogUtils.i("RockerActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        LogUtils.i("RockerActivity.OnUpdateTrafficFacility ");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        LogUtils.i("RockerActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        LogUtils.i("RockerActivity.updateAimlessModeStatistics  ");
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        LogUtils.i("RockerActivity.updateAimlessModeCongestionInfo ");
    }

    @Override
    public void onPlayRing(int i) {
        LogUtils.i("RockerActivity.onPlayRing  ");
    }

}
