package com.skyworth.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.CameraPosition;
import com.skyworth.activity.SkyworthActivity;
import com.skyworth.common.IKeyEvent;
import com.skyworth.map.MapController;
import com.skyworth.route.RoutePlanInfo;
import com.skyworth.utils.L;
import com.skyworth.navi.R;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements IKeyEvent,
        AMap.OnCameraChangeListener {

    private static final String TAG = "BaseFragment";
    protected MapController mMapController;
    protected RoutePlanInfo mRoutePlanInfo;
    protected View mRootView;
    protected View mLastFocusView;

    @Override
    public void onAttach(Context context) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onCreate");
        MapController mapController = ((SkyworthActivity) getActivity()).getMapController();
        mMapController = mapController;
        mMapController.setOnCameraChangeListener(this);

        mRoutePlanInfo = ((SkyworthActivity) getActivity()).getRoutePlanInfo();
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "getLayoutInflater");
        return super.getLayoutInflater(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onCreateView");
        L.d(TAG, "mRootView=" + mRootView);
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mRootView);

            init(mRootView, savedInstanceState);
        }

        return mRootView;
    }

    public abstract int getLayoutId();

    public abstract int getFirstFocusViewId();

    public abstract void init(View rootView, Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onResume");
        super.onResume();
        if (mLastFocusView == null) {
            mLastFocusView = mRootView.findViewById(getFirstFocusViewId());
        }
        mLastFocusView.requestFocus();
    }

    @Override
    public void onPause() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onPause");
        super.onPause();
        mLastFocusView = mRootView.findFocus();
    }

    @Override
    public void onStop() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onDetach");
        super.onDetach();
    }

    protected <V extends View> V findViewById(View v, int id) {
        return (V) v.findViewById(id);
    }

    @Override
    public boolean onBackPressed() {
        if (!isRootFragment()) {
            GlobalContext.getFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d(TAG, "onKeyDown:" + keyCode);
        return false;
    }

    // TODO: 2017/9/13
    private BaseFragment mCurrentFragment;
    private String FRAGMENT_TAG = "all";
    public void showFragment(BaseFragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(!fragment.isAdded()){
            ft.add(R.id.map_fragment_container,fragment,FRAGMENT_TAG);
        }

        if(mCurrentFragment != null){
            ft.hide(mCurrentFragment);
        }

        ft.show(fragment);
        ft.commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }

    public void hideFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(null != mCurrentFragment){
            ft.hide(mCurrentFragment);
        }
        ft.commitAllowingStateLoss();
        mCurrentFragment = null;
    }

    public int replaceFragmentToActivity(BaseFragment fragment) {
        return replaceFragmentToActivity(fragment, null);
    }

    public int replaceFragmentToActivity(BaseFragment fragment, String name) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.replace(R.id.map_fragment_container, fragment);
        ft.addToBackStack(name);
        int index = ft.commit();
        L.d(TAG, "replaceFragmentToActivity index=" + index);
        return index;
    }

    public int hideFragmentToActivity(BaseFragment toHide, BaseFragment toShow, String name) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.hide(toHide);
        ft.add(R.id.map_fragment_container, toShow);
        ft.addToBackStack(name);
        int index = ft.commit();
        L.d(TAG, "hideFragmentToActivity index=" + index);
        return index;
    }

    public int addFragmentToActivity(BaseFragment fragment) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.add(R.id.map_fragment_container, fragment);
        ft.addToBackStack(null);
        int index = ft.commit();
        L.d(TAG, "addFragmentToActivity index=" + index);
        return index;
    }

    public void popBackStack() {
        GlobalContext.getFragmentManager().popBackStack();
    }

    public int removeFragmentToActivity(BaseFragment fragment) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.remove(fragment);
        int index = ft.commit();
        L.d(TAG, "removeFragmentToActivity index=" + index);
        return index;
    }

    protected boolean isRootFragment() {
        return false;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }
}
