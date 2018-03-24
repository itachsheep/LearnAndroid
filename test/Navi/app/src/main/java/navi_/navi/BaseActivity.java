package navi_.navi;

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
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.List;

import speek.TTSController;
import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/4.
 */

public class BaseActivity extends Activity implements AMapNaviListener,AMapNaviViewListener{
    protected TTSController ttsController;
    protected AMapNavi mAMapNavi;
    protected NaviLatLng mEndLatlng = new NaviLatLng(40.084894,116.603039);
    protected NaviLatLng mStartLatlng = new NaviLatLng(39.825934,116.342972);
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    protected List<NaviLatLng> mWayPointList;
    protected AMapNaviView mAMapNaviView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("BaseActivity.onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //实例化语音引擎
        ttsController = TTSController.getInstance(getApplicationContext());
        ttsController.init();

        //AMapNavi 是导航对外控制类，提供计算规划路线、偏航以及拥堵重新算路等方法。注意：AMapNavi 不支持多实例。
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(ttsController);

        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);
    }

    @Override
    protected void onResume() {
        LogUtils.i("BaseActivity.onResume");
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        LogUtils.i("BaseActivity.onPause");
        super.onPause();
        mAMapNaviView.onPause();

//        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        ttsController.stopSpeaking();
//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("BaseActivity.onDestroy");
        super.onDestroy();
        mAMapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        ttsController.destroy();
    }


    /*************************************************
     * ************AMapNaviListener 相关接口*******************
     * ***********************************************
     */
    @Override
    public void onInitNaviFailure() {
        LogUtils.i("BaseActivity.onInitNaviFailure");
        Toast.makeText(this, "init navi_.navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
//初始化成功
        LogUtils.i("BaseActivity.onInitNaviSuccess");
    }

    @Override
    public void onStartNavi(int i) {
        LogUtils.i("BaseActivity.onStartNavi");
//开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        LogUtils.i("BaseActivity.onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        //当前位置回调
        LogUtils.i("BaseActivity.onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        LogUtils.i("BaseActivity.onGetNavigationText type: "+type+", text: "+text);
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtils.i("BaseActivity.onGetNavigationText s: "+s);
    }

    @Override
    public void onEndEmulatorNavi() {
//结束模拟导航
        LogUtils.i("BaseActivity.onEndEmulatorNavi ");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        LogUtils.i("BaseActivity.onArriveDestination ");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        LogUtils.i("--------------------------------------------");
        LogUtils.i("路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        LogUtils.i("错误码详细链接见：http://lbs.amap.com/api/android-navi_.navi-sdk/guide/tools/errorcode/");
        LogUtils.i("--------------------------------------------");
        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onReCalculateRouteForYaw() {
//偏航后重新计算路线回调
        LogUtils.i("BaseActivity.onReCalculateRouteForYaw ");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        LogUtils.i("BaseActivity.onReCalculateRouteForTrafficJam  ");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
//到达途径点
        LogUtils.i("BaseActivity.onArrivedWayPoint s: ");
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        //GPS开关状态回调
        LogUtils.i("BaseActivity.onGpsOpenStatus  ");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        LogUtils.i("BaseActivity.onNaviInfoUpdate ");
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
        //过时
        LogUtils.i("BaseActivity.onNaviInfoUpdated");
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
        LogUtils.i("BaseActivity.updateCameraInfo ");
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
        LogUtils.i("BaseActivity.onServiceAreaUpdate ");
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
        LogUtils.i("BaseActivity.showCross ");
    }

    @Override
    public void hideCross() {
//隐藏转弯回调
        LogUtils.i("BaseActivity.hideCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        //显示车道信息
        LogUtils.i("BaseActivity.showLaneInfo");
    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        LogUtils.i("BaseActivity.hideLaneInfo");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
//多路径算路成功回调
        LogUtils.i("BaseActivity.onCalculateRouteSuccess  ");
    }

    @Override
    public void notifyParallelRoad(int i) {
        LogUtils.i("BaseActivity.notifyParallelRoad ");
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
        LogUtils.i("BaseActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        LogUtils.i("BaseActivity.OnUpdateTrafficFacility ");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        LogUtils.i("BaseActivity.OnUpdateTrafficFacility  ");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        LogUtils.i("BaseActivity.updateAimlessModeStatistics  ");
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        LogUtils.i("BaseActivity.updateAimlessModeCongestionInfo ");
    }

    @Override
    public void onPlayRing(int i) {
        LogUtils.i("BaseActivity.onPlayRing  ");
    }


    /***************************************************
     * ***********************AMapNaviViewListener**************************
     * *************************************************
     */
    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
        LogUtils.i("BaseActivity.onNaviSetting");
    }

    @Override
    public void onNaviCancel() {
        LogUtils.i("BaseActivity.onNaviCancel ");
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        LogUtils.i("BaseActivity.onNaviBackClick");
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {
//地图的模式，锁屏或锁车
        LogUtils.i("BaseActivity.onNaviMapMode");
    }

    @Override
    public void onNaviTurnClick() {
//转弯view的点击回调
        LogUtils.i("BaseActivity.onNaviTurnClick ");
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
        LogUtils.i("BaseActivity.onNextRoadClick");
    }

    @Override
    public void onScanViewButtonClick() {
        LogUtils.i("BaseActivity.onScanViewButtonClick");
        //全览按钮点击回调
    }

    @Override
    public void onLockMap(boolean b) {
//锁地图状态发生变化时回调
        LogUtils.i("BaseActivity.onLockMap ");
    }

    @Override
    public void onNaviViewLoaded() {
        LogUtils.i( "BaseActivity.onLockMap 导航页面加载成功");
        LogUtils.i( "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");

    }
}
