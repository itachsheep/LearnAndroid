package rocker;

import android.app.Activity;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
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
    private ImageView mIcon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocker);
        mIcon = (ImageView) findViewById(R.id.myicon);
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
    private int strategyConvert;
    @Override
    public void onInitNaviSuccess() {
        //caculate route
        LogUtils.i("RockerActivity.onInitNaviSuccess");

        strategyConvert = mAmapNavi.strategyConvert(false, false, false, false, false);
        mAmapNavi.calculateDriveRoute(slist,elist,wlist,strategyConvert);
    }


    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //show route or start navi
        LogUtils.i("RockerActivity.onCalculateRouteSuccess");
        mAmapNavi.startNavi(NaviType.GPS);

    }

    //accurate parameters
    private double lng ;
    private double lat ;
    private float  accuracy =  1000.0f;
    private float     speed =  2.0f;
    private double startLat ;
    private double startLng ;
    private double    multi =   0.00000000000001;
    private double    multiX =   0.000001;
    private double    multiY =   0.000001;
//                             39.8260630099283
//    private Location location = new Location("gps");

    private double radian;
    private LatLng newLatLng;
    private Marker marker;
    private int i = 1;
    private boolean isFirst = true;
    private long time = 0;

    private double y;
    private double x;

    private float pivotX;
    private float pivotY;

    private void initListener() {
        mRockerView.setListener(new RockerView.RockerListener() {
            @Override
            public void callback(int eventType, int angle, float currentDistance) {
                switch (eventType) {
                    case RockerView.EVENT_ACTION:

                        synchronized (lockObj){
                            getScreenPivotAndNavi(angle, currentDistance);
                        }
                        break;
                }
            }
        });
    }

    /**
     * base on screen pivot to location, and then navi
     * @param angle
     * @param currentDistance
     */
    private void getScreenPivotAndNavi(int angle, float currentDistance) {
        if(angle != -1){
            radian = Math.toRadians(angle);
            y = currentDistance * Math.sin(radian);
            x = currentDistance * Math.cos(radian);


            if(isFirst){
                NaviLatLng startPoint = mAmapNavi.getNaviPath().getStartPoint();
                startLat = startPoint.getLatitude();
                startLng = startPoint.getLongitude();

                if(null == mAmap){
                    mAmap = mAmapNaviView.getMap();
                }

                Point point = mAmap.getProjection().toScreenLocation(new LatLng(startLat, startLng));
                pivotX = point.x;
                pivotY = point.y;
                mIcon.setX(pivotX);
                mIcon.setY(pivotY);
                mIcon.setVisibility(View.VISIBLE);

                isFirst = false;
            }
        }else {
//                                LogUtils.i("x: "+x+", y: "+y);
            pivotX = (float) (pivotX + x);
            pivotY = (float) (pivotY - y);
            mIcon.setX(pivotX);
            mIcon.setY(pivotY);

            LatLng latLng = mAmap.getProjection().fromScreenLocation(new Point((int)pivotX, (int)pivotY));
            time++;

            LogUtils.i("time : "+time+
                    ", x: "+pivotX+
                    ", y: "+pivotY+
                    ", newLat: "+latLng.latitude+
                    ", newLng: "+latLng.longitude);

            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLongitude(latLng.longitude);
            location.setLatitude(latLng.latitude);
            location.setSpeed(speed);
            location.setAccuracy(accuracy);
            location.setBearing(5);
            location.setTime(System.currentTimeMillis());


            //use gaode pivot = 2, use GPS pivot = 1
            mAmapNavi.setExtraGPSData(2,location);
        }
    }

    /**
     * move as coordlist pivot
     * @param angle
     */
    private void moveAsCoordlist(int angle) {
        if(angle == -1){
            AMapNaviPath naviPath = mAmapNavi.getNaviPath();
            NaviLatLng startPoint = naviPath.getStartPoint();
            List<NaviLatLng> coordList = naviPath.getCoordList();

            int size = naviPath.getCoordList().size();
            if(i > size){
                i = size;
            }
            i++;
            NaviLatLng naviLatLng = naviPath.getCoordList().get(i);
            String name = "gps"+i;
            Location location = new Location(name);
            location.setLongitude(naviLatLng.getLongitude());
            location.setLatitude(naviLatLng.getLatitude());
            LogUtils.i("i : "+i+ ", lat: "+naviLatLng.getLatitude()+
                    ", lng: "+naviLatLng.getLongitude());
            location.setSpeed(speed);
            location.setAccuracy(accuracy);
            location.setBearing(5);
            location.setTime(System.currentTimeMillis());

            //use gaode pivot = 2, use GPS pivot = 1
            mAmapNavi.setExtraGPSData(2,location);
        }
    }

    /**
     * move map marker, and then recalculate the route
     * @param angle
     * @param currentDistance
     */
    private void selefModeNavi(int angle, float currentDistance) {
        if(angle == -1){
            //set extra gps dat
            slist.clear();
            NaviLatLng latlng = new NaviLatLng(newLatLng.latitude,newLatLng.longitude);
            slist.add(latlng);
            LogUtils.i("calcuate route once again...");
            mAmapNavi.calculateDriveRoute(slist,elist,wlist,strategyConvert);

        }else {
            if(null == mAmap){
                mAmap = mAmapNaviView.getMap();
            }
            radian = Math.toRadians(angle);
//                                LogUtils.i(" y: "+ currentDistance* Math.sin(radian)+ ", x: "+currentDistance * Math.cos(radian));
            lat = currentDistance * Math.sin(radian) * multi;
            lng = currentDistance * Math.cos(radian) * multi;
            List<Marker> markers = mAmap.getMapScreenMarkers();
            marker = markers.get(0);
            LatLng latLng = marker.getPosition();
            newLatLng = new LatLng(latLng.latitude+lat,latLng.longitude+lng);
            marker.setPosition(newLatLng);
            //设置中心点和缩放比例
            mAmap.moveCamera(CameraUpdateFactory.changeLatLng(newLatLng));
        }
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
        mAmapNavi.removeAMapNaviListener(this);
        mAmapNaviView.onDestroy();
        mAmapNavi = null;
        mAmapNaviView = null;
        System.gc();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
