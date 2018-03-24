package navi_.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapRestrictionInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.skyworth.navi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import navi_.navi.ErrorInfo;
import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/4.
 */

public class RestRouteShowActivity extends Activity implements AMapNaviListener,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {




    private EditText editText;



    /**
     * 选择起点Action标志位
     */
    private boolean mapClickStartReady;

    /**
     * 选择终点Aciton标志位
     */
    private boolean mapClickEndReady;
    private NaviLatLng endLatlng = new NaviLatLng(39.955846, 116.352765);
    private NaviLatLng startLatlng = new NaviLatLng(39.925041, 116.437901);
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /**
     * 终点坐标集合［建议就一个终点］
     */
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    /**
     * 地图对象
     */
    private MapView mRouteMapView;
    private Marker mStartMarker;
    private Marker mEndMarker;
    /**
     * 导航对象(单例)
     */
    private AMapNavi mAMapNavi;
    private AMap mAmap;


    /**
     * 途径点坐标集合
     */
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();

    /**
     * 路线计算成功标志位
     */
    private boolean calculateSuccess = false;
    private boolean chooseRouteSuccess = false;

    /**
     * 当前用户选中的路线，在下个页面进行导航
     */
    private int routeIndex;

    /**
     * 路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线
     **/
    private int zindex = 1;
    //
    private boolean congestion, cost, hightspeed, avoidhightspeed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_calculate);
        editText = (EditText) findViewById(R.id.car_number);
        CheckBox congestion = (CheckBox) findViewById(R.id.congestion);
        CheckBox cost = (CheckBox) findViewById(R.id.cost);
        CheckBox hightspeed = (CheckBox) findViewById(R.id.hightspeed);
        CheckBox avoidhightspeed = (CheckBox) findViewById(R.id.avoidhightspeed);
        Button calculate = (Button) findViewById(R.id.calculate);
        final Button startPoint = (Button) findViewById(R.id.startpoint);
        Button endPoint = (Button) findViewById(R.id.endpoint);
        Button selectroute = (Button) findViewById(R.id.selectroute);
        Button gpsnavi = (Button) findViewById(R.id.gpsnavi);
        Button emulatornavi = (Button) findViewById(R.id.emulatornavi);
        calculate.setOnClickListener(this);
        startPoint.setOnClickListener(this);
        endPoint.setOnClickListener(this);
        selectroute.setOnClickListener(this);
        gpsnavi.setOnClickListener(this);
        emulatornavi.setOnClickListener(this);
        congestion.setOnCheckedChangeListener(this);
        cost.setOnCheckedChangeListener(this);
        hightspeed.setOnCheckedChangeListener(this);
        avoidhightspeed.setOnCheckedChangeListener(this);

        mRouteMapView = (MapView) findViewById(R.id.navi_view);
        mRouteMapView.onCreate(savedInstanceState);

        mAmap = mRouteMapView.getMap();
        mAmap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //控制选起点
                if (mapClickStartReady) {
                    startLatlng = new NaviLatLng(latLng.latitude,latLng.longitude);
                    mStartMarker.setPosition(latLng);
                    startList.clear();
                    startList.add(startLatlng);
                    mapClickStartReady = false;
                }

                //控制选终点
                if (mapClickEndReady) {
                    endLatlng = new NaviLatLng(latLng.latitude, latLng.longitude);
                    mEndMarker.setPosition(latLng);
                    endList.clear();
                    endList.add(endLatlng);
                    mapClickEndReady = false;
                }
            }
        });

        // 初始化Marker添加到地图
        mStartMarker = mAmap.addMarker(new MarkerOptions().icon(
                BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),R.drawable.start))));
        mEndMarker = mAmap.addMarker(new MarkerOptions().icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.end))
        ));

        //添加导航监听事件
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
    }
    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calculate:
                //start calculate route
                clearRoute();
                mapClickStartReady = false;
                mapClickEndReady = false;
                if (avoidhightspeed && hightspeed) {
                    Toast.makeText(getApplicationContext(), "不走高速与高速优先不能同时为true.", Toast.LENGTH_LONG).show();
                }
                if (cost && hightspeed) {
                    Toast.makeText(getApplicationContext(), "高速优先与避免收费不能同时为true.", Toast.LENGTH_LONG).show();
                }

                /*
                 * strategyFlag转换出来的值都对应PathPlanningStrategy常量，用户也可以直接传入PathPlanningStrategy常量进行算路。
                 * 如:mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList,PathPlanningStrategy.DRIVING_DEFAULT);
                 */
                int strategyFlag = 0;
                try {
                    strategyFlag = mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (strategyFlag >= 0) {
                    String carNumber = editText.getText().toString();
                    AMapCarInfo carInfo = new AMapCarInfo();
                    //设置车牌
                    carInfo.setCarNumber(carNumber);
                    //设置车牌是否参与限行算路
                    carInfo.setRestriction(true);
                    mAMapNavi.setCarInfo(carInfo);
                    mAMapNavi.calculateDriveRoute(startList,endList,wayList,strategyFlag);
                }
                break;

            case R.id.startpoint:
                //ensure start point
                Toast.makeText(this, "请在地图上点选起点", Toast.LENGTH_SHORT).show();
                mapClickStartReady = true;
                break;
            case R.id.endpoint:
                Toast.makeText(this, "请在地图上点选终点", Toast.LENGTH_SHORT).show();
                mapClickEndReady = true;

            case R.id.selectroute:
                changeRoute();
                break;

            case R.id.gpsnavi:
                Intent gpsintent = new Intent(getApplicationContext(), RouteNaviActivity.class);
                gpsintent.putExtra("gps", true);
                startActivity(gpsintent);
                break;
            case R.id.emulatornavi:
                Intent intent = new Intent(getApplicationContext(), RouteNaviActivity.class);
                intent.putExtra("gps", false);
                startActivity(intent);
                break;
        }
    }

    public void changeRoute() {
        if (!calculateSuccess) {
            Toast.makeText(this, "请先算路", Toast.LENGTH_SHORT).show();
            return;
        }

        if (routeOverlays.size() == 1) {
            chooseRouteSuccess = true;
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
            Toast.makeText(this, "导航距离:" + (mAMapNavi.getNaviPath()).getAllLength() + "m" + "\n" + "导航时间:" + (mAMapNavi.getNaviPath()).getAllTime() + "s", Toast.LENGTH_SHORT).show();
            return;
        }

        if (routeIndex >= routeOverlays.size())
            routeIndex = 0;
        int routeID = routeOverlays.keyAt(routeIndex);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.4f);
        }
        routeOverlays.get(routeID).setTransparency(1);
        /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
        routeOverlays.get(routeID).setZindex(zindex++);

        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
        Toast.makeText(this, "路线标签:" + mAMapNavi.getNaviPath().getLabels(), Toast.LENGTH_SHORT).show();
        routeIndex++;
        chooseRouteSuccess = true;

        /**选完路径后判断路线是否是限行路线**/
        AMapRestrictionInfo info = mAMapNavi.getNaviPath().getRestrictionInfo();
        if (!TextUtils.isEmpty(info.getRestrictionTitle())) {
            Toast.makeText(this, info.getRestrictionTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mRouteMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mRouteMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRouteMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();
        mRouteMapView.onDestroy();
        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.destroy();

    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
        calculateSuccess = true;
        mAmap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(mAmap, path, this);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    /*****************************导航AMapNaviListener接口************************************************
     * ***************************************************************************
     * ***************************************************************************
     */

    @Override
    public void onInitNaviFailure() {
        LogUtils.i("RestRouteShowActivity.onInitNaviFailure");
        Toast.makeText(this, "init navi_.navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
//多路径算路成功回调
        LogUtils.i("RestRouteShowActivity.onCalculateRouteSuccess  ");
        //清空上次计算的路径列表。
        routeOverlays.clear();
        HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
        for (int i = 0; i < ints.length; i++) {
            AMapNaviPath path = paths.get(ints[i]);
            if (path != null) {
                drawRoutes(ints[i], path);
            }
        }
    }
    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        calculateSuccess = false;
        Toast.makeText(getApplicationContext(), "计算路线失败，errorcode＝" + errorInfo, Toast.LENGTH_SHORT).show();

        LogUtils.i("--------------------------------------------");
        LogUtils.i("路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        LogUtils.i("错误码详细链接见：http://lbs.amap.com/api/android-navi_.navi-sdk/guide/tools/errorcode/");
        LogUtils.i("--------------------------------------------");
        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onInitNaviSuccess() {
//初始化成功
        LogUtils.i("RestRouteShowActivity.onInitNaviSuccess");
    }

    @Override
    public void onStartNavi(int i) {
        LogUtils.i("RestRouteShowActivity.onStartNavi");
//开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        LogUtils.i("RestRouteShowActivity.onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        //当前位置回调
        LogUtils.i("RestRouteShowActivity.onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        LogUtils.i("RestRouteShowActivity.onGetNavigationText type: "+type+", text: "+text);
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtils.i("RestRouteShowActivity.onGetNavigationText s: "+s);
    }

    @Override
    public void onEndEmulatorNavi() {
//结束模拟导航
        LogUtils.i("RestRouteShowActivity.onEndEmulatorNavi ");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        LogUtils.i("RestRouteShowActivity.onArriveDestination ");
    }



    @Override
    public void onReCalculateRouteForYaw() {
//偏航后重新计算路线回调
        LogUtils.i("RestRouteShowActivity.onReCalculateRouteForYaw ");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        LogUtils.i("RestRouteShowActivity.onReCalculateRouteForTrafficJam  ");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
//到达途径点
        LogUtils.i("RestRouteShowActivity.onArrivedWayPoint s: ");
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        //GPS开关状态回调
        LogUtils.i("RestRouteShowActivity.onGpsOpenStatus  ");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        LogUtils.i("RestRouteShowActivity.onNaviInfoUpdate ");
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
        //过时
        LogUtils.i("RestRouteShowActivity.onNaviInfoUpdated");
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
        LogUtils.i("RestRouteShowActivity.updateCameraInfo ");
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
        LogUtils.i("RestRouteShowActivity.onServiceAreaUpdate ");
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
        LogUtils.i("RestRouteShowActivity.showCross ");
    }

    @Override
    public void hideCross() {
//隐藏转弯回调
        LogUtils.i("RestRouteShowActivity.hideCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        //显示车道信息
        LogUtils.i("RestRouteShowActivity.showLaneInfo");
    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        LogUtils.i("RestRouteShowActivity.hideLaneInfo");
    }



    @Override
    public void notifyParallelRoad(int i) {
        LogUtils.i("RestRouteShowActivity.notifyParallelRoad ");
        if (i == 0) {
            Toast.makeText(this, "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            LogUtils.i( "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(this, "当前在主路", Toast.LENGTH_SHORT).show();

            LogUtils.i(  "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(this, "当前在辅路", Toast.LENGTH_SHORT).show();

            LogUtils.i(  "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //更新交通设施信息
        LogUtils.i("RestRouteShowActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        LogUtils.i("RestRouteShowActivity.OnUpdateTrafficFacility ");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        LogUtils.i("RestRouteShowActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        LogUtils.i("RestRouteShowActivity.updateAimlessModeStatistics  ");
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        LogUtils.i("RestRouteShowActivity.updateAimlessModeCongestionInfo ");
    }

    @Override
    public void onPlayRing(int i) {
        LogUtils.i("RestRouteShowActivity.onPlayRing  ");
    }
}
