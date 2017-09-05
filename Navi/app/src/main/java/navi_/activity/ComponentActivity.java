package navi_.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.amap.api.navi.AmapRouteActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.skyworth.navi.R;

import java.util.ArrayList;
import java.util.List;

import speek.AmapTTSController;
import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/4.
 */

public class ComponentActivity extends Activity implements INaviInfoCallback {
    private String[] examples = new String[]{"起终点算路", "无起点算路", "途径点算路"};


    private AmapTTSController amapTTSController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        amapTTSController = AmapTTSController.getInstance(getApplicationContext());
        amapTTSController.init();
    }

    LatLng p1 = new LatLng(39.993266, 116.473193);//首开广场
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站
    LatLng p4 = new LatLng(39.773801, 116.368984);//新三余公园(南5环)
    LatLng p5 = new LatLng(40.041986, 116.414496);//立水桥(北5环)

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            switch (position){
                case 0:
                    //从北京站 -> 故宫博物院
                    AmapNaviPage.getInstance().showRouteActivity(
                            getApplicationContext(),
                            new AmapNaviParams(new Poi("北京站", p3, ""), null,
                            new Poi("故宫博物院", p5, ""),
                            AmapNaviType.DRIVER), ComponentActivity.this);

                    break;
                case 1:
                    //无起点 从我的位置 -> 故宫博物院
                    /*AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(),
                            new AmapNaviParams(null,
                            null, new Poi("故宫博物院", p2, ""),
                            AmapNaviType.DRIVER), ComponentActivity.this);*/

                    /*AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(),
                            new AmapNaviParams(null,
                                    null, null,
                                    AmapNaviType.DRIVER), ComponentActivity.this);*/

                    Intent intent = new Intent(ComponentActivity.this,AmapRouteActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    //立水桥  -> 新三余公园
                    List<Poi> poiList = new ArrayList();
                    poiList.add(new Poi("首开广场", p1, ""));
                    poiList.add(new Poi("故宫博物院", p2, ""));
                    poiList.add(new Poi("北京站", p3, ""));
                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(),
                            new AmapNaviParams(new Poi("立水桥(北5环)", p5, ""),
                                    poiList,
                                    new Poi("新三余公园(南5环)", p4, ""),
                                    AmapNaviType.DRIVER),
                            ComponentActivity.this);

                    break;
            }
        }
    };



    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
        setTitle("导航SDK " + AMapNavi.getVersion());
        listView.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public void onInitNaviFailure() {
        LogUtils.i("onInitNaviFailure");
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtils.i("onGetNavigationText s : "+s);
        amapTTSController.onGetNavigationText(s);
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        LogUtils.i("onLocationChange");
    }

    @Override
    public void onArriveDestination(boolean b) {
        LogUtils.i("onArriveDestination");
    }

    @Override
    public void onStartNavi(int i) {
        LogUtils.i("onStartNavi");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        LogUtils.i("onCalculateRouteSuccess");
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        LogUtils.i("onInitNaviFailure");
    }

    @Override
    public void onStopSpeaking() {
        LogUtils.i("onStopSpeaking");
        amapTTSController.stopSpeaking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy");
        amapTTSController.destroy();
    }
}
