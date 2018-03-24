package com.skyworth.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.skyworth.base.BaseActivity;
import com.skyworth.base.BaseFragment;
import com.skyworth.base.DebugApplication;
import com.skyworth.fragment.MainMapFragment;
import com.skyworth.map.MapController;
import com.skyworth.navi.R;
import com.skyworth.protocol.IVoiceControl;
import com.skyworth.route.RoutePlanInfo;

/**
 * Created by SDT14324 on 2017/9/12.
 */

public class SkyworthActivity extends BaseActivity implements IVoiceControl {
    String tag = SkyworthActivity.class.getSimpleName();
    private MapController mMapController;
    private RoutePlanInfo mRoutePlanInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skyworth);

        mRoutePlanInfo = new RoutePlanInfo();

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        mMapController = new MapController(this, mAMapNaviView);
        mMapController.onCreate(savedInstanceState);

        AMapNavi.getInstance(this).startGPS();

//        L.i(tag,"file dir: "+this.getFilesDir().toString()+
//        ", external dir: "+this.getExternalCacheDir().toString());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BaseFragment newFragment = MainMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.map_fragment_container, newFragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapController.onStart();

//        ProtocolSender.sendState(ProtocolConstant.STATE_ACTIVITY_FOREGROUND);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapController.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapController.onStop();

//        ProtocolSender.sendState(ProtocolConstant.STATE_ACTIVITY_BACKGROUND);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapController.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mMapController.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapController.onDestroy();
        // TODO: 2017/9/13
        DebugApplication.getRefWatcher(this).watch(this);
    }


    public void onDump(View view) {
        // TODO: 2017/9/12
        dump();
    }


    public MapController getMapController() {
        return mMapController;
    }

    public RoutePlanInfo getRoutePlanInfo() {
        return mRoutePlanInfo;
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void minApp() {
        // TODO: 2017/9/12
        moveTaskToBack(false);
    }

    @Override
    public void exitNavi() {

    }

    @Override
    public void showRoadCondition(int toBe) {
//        mMapController.setTrafficLine(toBe == ProtocolConstant.OPEN_ROAD_CONDITION);
    }

    @Override
    public void zoomMap(int toBe) {

    }

    @Override
    public void switchVisualMode(int toBe) {

    }

    @Override
    public void showNaviPreview() {

    }

    @Override
    public void setNaviRoutePrefer(int toBe) {

    }

    @Override
    public void switchDayNightMode(int toBe) {

    }

    @Override
    public void naviWithDest(Intent intent) {

    }
}
