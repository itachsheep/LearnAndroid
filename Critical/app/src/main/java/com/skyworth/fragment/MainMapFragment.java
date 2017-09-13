package com.skyworth.fragment;

import android.os.Bundle;
import android.view.View;

import com.skyworth.base.BaseFragment;
import com.skyworth.navi.R;
import com.skyworth.view.MapInteractiveView;

import butterknife.OnClick;

/**
 * Created by SDT14324 on 2017/9/13.
 */

public class MainMapFragment extends BaseFragment {
    private static MainMapFragment mapFragment;

    public static MainMapFragment newInstance() {
        synchronized (MainMapFragment.class){
            if(mapFragment == null){
                mapFragment = new MainMapFragment();
            }
            return mapFragment;
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.main_map_fragment;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.ib_go_search;
    }


//    @BindView(R.id.map_interactive_view)
    public MapInteractiveView mMapInteractiveView;

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        mMapInteractiveView = (MapInteractiveView) rootView.findViewById(R.id.map_interactive_view);
//        mMapInteractiveView.setMapController(mMapController);
//        mMapInteractiveView.showTrafficLineBtn(true);
//        mMapInteractiveView.showVisualModeBtn(true);
//        mMapInteractiveView.showZoomLayout(true);
    }

    @OnClick(R.id.ib_go_search)
    public void goToSearch(){
        showFragment(MajorSearchFragment.newInstance());
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapController.showMyLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
