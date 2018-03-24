package com.amap.navi.demo.activity.custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.zyxnet.autonavi.R;
import com.amap.navi.demo.activity.CustomCarActivity;
import com.amap.navi.demo.activity.CustomDirectionViewActivity;
import com.amap.navi.demo.activity.CustomDriveWayViewActivity;
import com.amap.navi.demo.activity.CustomNextTurnTipViewActivity;
import com.amap.navi.demo.activity.CustomRouteActivity;
import com.amap.navi.demo.activity.CustomRouteTextureInAMapNaviViewActivity;
import com.amap.navi.demo.activity.CustomTrafficBarViewActivity;
import com.amap.navi.demo.activity.CustomTrafficButtonViewActivity;
import com.amap.navi.demo.activity.CustomZoomButtonViewActivity;
import com.amap.navi.demo.activity.CustomZoomInIntersectionViewActivity;
import com.amap.navi.demo.activity.NorthModeActivity;
import com.amap.navi.demo.activity.OverviewModeActivity;
import com.amap.navi.demo.util.AmapTTSController;

import java.util.ArrayList;
import java.util.List;

public class ComponentActivity extends Activity implements INaviInfoCallback {
    private String[] examples = new String[]{"起终点算路", "无起点算路", "途径点算路"};
    private AmapTTSController amapTTSController;
    LatLng p1 = new LatLng(39.993266, 116.473193);//首开广场
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站
    LatLng p4 = new LatLng(39.773801, 116.368984);//新三余公园(南5环)
    LatLng p5 = new LatLng(40.041986, 116.414496);//立水桥(北5环)

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("北京站", p3, ""), null, new Poi("故宫博物院", p5, ""), AmapNaviType.DRIVER), ComponentActivity.this);
            } else if (position == 1) {
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(null, null, new Poi("故宫博物院", p2, ""), AmapNaviType.DRIVER), ComponentActivity.this);
            } else if (position == 2) {
                List<Poi> poiList = new ArrayList();
                poiList.add(new Poi("首开广场", p1, ""));
                poiList.add(new Poi("故宫博物院", p2, ""));
                poiList.add(new Poi("北京站", p3, ""));
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("立水桥(北5环)", p5, ""), poiList, new Poi("新三余公园(南5环)", p4, ""), AmapNaviType.DRIVER), ComponentActivity.this);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        // SpeechUtils.getInstance(this).speakText();系统自带的语音播报
        amapTTSController = AmapTTSController.getInstance(getApplicationContext());
        amapTTSController.init();

    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
        setTitle("导航SDK " + AMapNavi.getVersion());
        listView.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onGetNavigationText(String s) {
        amapTTSController.onGetNavigationText(s);
    }

    @Override
    public void onStopSpeaking() {
        amapTTSController.stopSpeaking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        amapTTSController.destroy();
    }
}
