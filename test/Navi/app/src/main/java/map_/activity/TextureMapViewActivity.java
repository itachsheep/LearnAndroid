package map_.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.skyworth.navi.R;

/**
 * Created by SDT14324 on 2017/9/6.
 */

public class TextureMapViewActivity extends Activity {
    private TextureMapView mapView;
    private AMap aMap;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_texturemap_activity);
        mapView = (TextureMapView) findViewById(R.id.map);
//        frameLayout = (FrameLayout) findViewById(R.id.texture_fl);
        mapView.onCreate(savedInstanceState);
        if(aMap == null){
            aMap = mapView.getMap();
        }

    }

    public void showMapFragment(View view){

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
}
