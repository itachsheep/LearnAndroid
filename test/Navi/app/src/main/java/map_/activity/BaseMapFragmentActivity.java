package map_.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapFragment;
import com.skyworth.navi.R;

import utils.LogUtils;

/**
 * Created by SDT14324 on 2017/9/5.
 */

public class BaseMapFragmentActivity extends Activity {
    private AMap aMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basemap_fragment_activity);
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if(aMap == null){
            aMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_tao)).getMap();
            LogUtils.i("setUpMapIfNeeded amap: "+aMap.toString());
        }
    }
}
