package rocker;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
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
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.gcssloop.widget.RockerView;
import com.skyworth.navi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/6.
 */

public class MapRockerActivity extends Activity implements AMap.OnMyLocationChangeListener, AMap.OnMapLoadedListener, AMapNaviListener {
    private MapView mapView;
    private AMap aMap;
    private Point point;
    private RockerView rockerView;
    private AMapNavi aMapNavi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_rocker);
        mapView = findViewById(R.id.map);
        rockerView = findViewById(R.id.rocker);

        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        //show blue location point
       /* MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);*/
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapLoadedListener(this);

        //init AmapNavi
        aMapNavi = AMapNavi.getInstance(getApplicationContext());
        aMapNavi.addAMapNaviListener(this);

        initListener();
    }

    private List<NaviLatLng> slist = new ArrayList<>();
    private List<NaviLatLng> elist = new ArrayList<>();
    private List<NaviLatLng> waylist = new ArrayList<>();
    @Override
    public void onInitNaviSuccess() {
        //calculate route
        LogUtils.i("onInitNaviSuccess ");
        slist.add(new NaviLatLng(22.539036, 113.950747));
        elist.add(new NaviLatLng(22.539036, 114.950747));
        int strategy = aMapNavi.strategyConvert(false, false, false, false, false);
        aMapNavi.calculateDriveRoute(slist,elist,waylist,strategy);

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //show route or start navi
        LogUtils.i("onCalculateRouteSuccess ");
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();
        for (int i = 0; i < ints.length; i++) {
            AMapNaviPath path = paths.get(ints[i]);
            if (path != null) {
                drawRoutes(ints[i], path);
            }
        }
    }
    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();
    private void drawRoutes(int routeId, AMapNaviPath path) {

//        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(aMap, path, this);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    private double lng = 0.00001;
    private double lat = 0.00001;
    private double radian;
    private double multi = 0.0000001;
    private double x ;
    private double y;
    private void initListener() {
        rockerView.setListener(new RockerView.RockerListener() {
            @Override
            public void callback(int eventType, int currentAngle, float currentDistance) {
                switch (eventType) {
                    case RockerView.EVENT_ACTION:
                        if(currentAngle == -1){
                            return;
                        }
                        synchronized (MapRockerActivity.this){
                            radian = Math.toRadians(currentAngle);
                            LogUtils.i("########## currentDistance: "+currentDistance+
                                    ", y: "+ currentDistance* Math.sin(radian)+
                                    ", x: "+currentDistance * Math.cos(radian));
                            y = currentDistance * Math.sin(radian);
                            x = currentDistance * Math.cos(radian);
                            // add longtitude,East West
                            lng = x * multi;
                            //add latitude,North South
                            lat = y * multi;

                            List<Marker> markers = aMap.getMapScreenMarkers();
                            Marker marker = markers.get(0);
                            LatLng latLng = marker.getPosition();
                            LatLng newLatLng = new LatLng(latLng.latitude+lat,latLng.longitude+lng);
                            marker.setPosition(newLatLng);
                            //设置中心点和缩放比例
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(newLatLng));
//                            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onMapLoaded() {
//        LatLng latLng = new LatLng(39.90403, 116.407525);
        LatLng latLng = new LatLng(22.539036, 113.950747);
        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));


        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),
                        R.drawable.location_marker)));
        markerOption.setFlat(true);
        aMap.addMarker(markerOption);


        point = aMap.getProjection().toScreenLocation(latLng);
        LogUtils.i("pint x: "+point.x+", y: "+point.y);
    }



    @Override
    public void onMyLocationChange(Location location) {
       /* //得到锚点横坐标方向的偏移量。
        float anchorU = myLocationStyle.getAnchorU();
        float anchorV = myLocationStyle.getAnchorV();
        LogUtils.i("anchorU: "+anchorU+",anchorV:  "+anchorV );

        //返回当前定位源（locationSource）提供的定位信息
        Location myLocation = aMap.getMyLocation();*/


        //LatLng(double latitude, double longitude) 使用传入的经纬度构造LatLng 对象，一对经纬度值代表地球上一个地点。
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        Point point = aMap.getProjection().toScreenLocation(latLng);
        LogUtils.i("latitue: "+latitude+", longtittue: "+longitude+"pint x: "+point.x+", y: "+point.y);

       /* float accuracy = location.getAccuracy();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float bearing = location.getBearing();
        float speed = location.getSpeed();
        LogUtils.i("MapRockerActivity onMyLocationChange accuracy: "+accuracy+
                ",latitude: "+latitude+
                ",longitude: "+longitude+
                ",bearing: "+bearing+
                ",speed: "+speed);*/
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /*
    ***********************************AMapNaviListener**************************************************
     */

    @Override
    public void onInitNaviFailure() {

    }



    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }



    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }


}
