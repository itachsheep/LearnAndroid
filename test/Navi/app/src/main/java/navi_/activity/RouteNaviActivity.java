package navi_.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
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
import com.autonavi.tbt.TrafficFacilityInfo;
import com.skyworth.navi.R;

import speek.TTSController;
import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/5.
 */

public class RouteNaviActivity extends Activity implements AMapNaviListener, AMapNaviViewListener {

    AMapNaviView mAMapNaviView;
    AMapNavi mAMapNavi;
    TTSController mTtsManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_basic_navi);
        LogUtils.i("RouteNaviActivity.onCreate");
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();

        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);
        mAMapNavi.setEmulatorNaviSpeed(60);

        boolean gps = getIntent().getBooleanExtra("gps", false);
        if (gps) {
            mAMapNavi.startNavi(AMapNavi.GPSNaviMode);
        } else {
            mAMapNavi.startNavi(AMapNavi.EmulatorNaviMode);
        }
    }

    @Override
    protected void onResume() {
        LogUtils.i("RouteNaviActivity.onResume");
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        LogUtils.i("RouteNaviActivity.onPause");
        super.onPause();
        mAMapNaviView.onPause();
        //        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        mTtsManager.stopSpeaking();
        //
        //        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
        //        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("RouteNaviActivity.onDestroy");
        super.onDestroy();
        mAMapNaviView.onDestroy();
        mAMapNavi.stopNavi();
        /**
         * 当前页面不销毁AmapNavi对象。
         * 因为可能会返回到RestRouteShowActivity页面再次进行路线选择，然后再次进来导航。
         * 如果销毁了就没办法在上一个页面进行选择路线了。
         * 但是AmapNavi对象始终销毁，那我们就需要在上一个页面用户回退时候销毁了。
         */
//		mAMapNavi.destroy();
        mTtsManager.destroy();
    }


    /*************************************************
     * ************AMapNaviListener 相关接口*******************
     * ***********************************************
     */
    @Override
    public void onInitNaviFailure() {
        LogUtils.i("RouteNaviActivity.onInitNaviFailure");
        Toast.makeText(this, "init navi_.navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
//初始化成功
        LogUtils.i("RouteNaviActivity.onInitNaviSuccess");
    }

    @Override
    public void onStartNavi(int i) {
        LogUtils.i("RouteNaviActivity.onStartNavi");
//开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        LogUtils.i("RouteNaviActivity.onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        //当前位置回调
        LogUtils.i("RouteNaviActivity.onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        LogUtils.i("RouteNaviActivity.onGetNavigationText type: "+type+", text: "+text);
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtils.i("RouteNaviActivity.onGetNavigationText s: "+s);
    }

    @Override
    public void onEndEmulatorNavi() {
//结束模拟导航
        LogUtils.i("RouteNaviActivity.onEndEmulatorNavi ");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        LogUtils.i("RouteNaviActivity.onArriveDestination ");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        LogUtils.i("RouteNaviActivity.onCalculateRouteFailure ");
        //路线计算失败
//        LogUtils.i("--------------------------------------------");
//        LogUtils.i("路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
//        LogUtils.i("错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
//        LogUtils.i("--------------------------------------------");
//        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onReCalculateRouteForYaw() {
//偏航后重新计算路线回调
        LogUtils.i("RouteNaviActivity.onReCalculateRouteForYaw ");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        LogUtils.i("RouteNaviActivity.onReCalculateRouteForTrafficJam  ");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
//到达途径点
        LogUtils.i("RouteNaviActivity.onArrivedWayPoint s: ");
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        //GPS开关状态回调
        LogUtils.i("RouteNaviActivity.onGpsOpenStatus  ");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        LogUtils.i("RouteNaviActivity.onNaviInfoUpdate ");
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
        //过时
        LogUtils.i("RouteNaviActivity.onNaviInfoUpdated");
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
        LogUtils.i("RouteNaviActivity.updateCameraInfo ");
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
        LogUtils.i("RouteNaviActivity.onServiceAreaUpdate ");
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
        LogUtils.i("RouteNaviActivity.showCross ");
    }

    @Override
    public void hideCross() {
//隐藏转弯回调
        LogUtils.i("RouteNaviActivity.hideCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        //显示车道信息
        LogUtils.i("RouteNaviActivity.showLaneInfo");
    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        LogUtils.i("RouteNaviActivity.hideLaneInfo");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
//多路径算路成功回调
        LogUtils.i("RouteNaviActivity.onCalculateRouteSuccess  ");
    }

    @Override
    public void notifyParallelRoad(int i) {
        LogUtils.i("RouteNaviActivity.notifyParallelRoad ");
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
        LogUtils.i("RouteNaviActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        LogUtils.i("RouteNaviActivity.OnUpdateTrafficFacility ");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        LogUtils.i("RouteNaviActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        LogUtils.i("RouteNaviActivity.updateAimlessModeStatistics  ");
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        LogUtils.i("RouteNaviActivity.updateAimlessModeCongestionInfo ");
    }

    @Override
    public void onPlayRing(int i) {
        LogUtils.i("RouteNaviActivity.onPlayRing  ");
    }


    /***************************************************
     * ***********************AMapNaviViewListener**************************
     * *************************************************
     */
    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
        LogUtils.i("RouteNaviActivity.onNaviSetting");
    }

    @Override
    public void onNaviCancel() {
        LogUtils.i("RouteNaviActivity.onNaviCancel ");
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        LogUtils.i("RouteNaviActivity.onNaviBackClick");
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {
//地图的模式，锁屏或锁车
        LogUtils.i("RouteNaviActivity.onNaviMapMode");
    }

    @Override
    public void onNaviTurnClick() {
//转弯view的点击回调
        LogUtils.i("RouteNaviActivity.onNaviTurnClick ");
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
        LogUtils.i("RouteNaviActivity.onNextRoadClick");
    }

    @Override
    public void onScanViewButtonClick() {
        LogUtils.i("RouteNaviActivity.onScanViewButtonClick");
        //全览按钮点击回调
    }

    @Override
    public void onLockMap(boolean b) {
//锁地图状态发生变化时回调
        LogUtils.i("RouteNaviActivity.onLockMap ");
    }

    @Override
    public void onNaviViewLoaded() {
        LogUtils.i( "RouteNaviActivity.onLockMap 导航页面加载成功");
//        LogUtils.i( "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");

    }
}
