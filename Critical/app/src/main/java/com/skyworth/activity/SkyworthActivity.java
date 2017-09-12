package com.skyworth.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.navi.AMapNaviView;
import com.skyworth.base.BaseActivity;
import com.skyworth.map.MapController;
import com.skyworth.navi.R;
import com.skyworth.route.RoutePlanInfo;

/**
 * Created by SDT14324 on 2017/9/12.
 */

public class SkyworthActivity extends BaseActivity {
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
    }

    public MapController getMapController() {
        return mMapController;
    }

    public RoutePlanInfo getRoutePlanInfo() {
        return mRoutePlanInfo;
    }
}
