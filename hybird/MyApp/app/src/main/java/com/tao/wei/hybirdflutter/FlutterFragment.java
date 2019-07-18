package com.tao.wei.hybirdflutter;

import android.app.Activity;

import com.taobao.idlefish.flutterboost.containers.BoostFlutterFragment;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.PluginRegistry;

public class FlutterFragment extends BoostFlutterFragment {


    @Override
    public String getContainerName() {
        return "flutterFragment";
    }

    @Override
    public Map getContainerParams() {
        Map<String,String> params = new HashMap<>();
        //params.put("tag",getArguments().getString("tag"));
        return params;
    }

    @Override
    public void onRegisterPlugins(PluginRegistry registry) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void destroyContainer() {

    }
    public static FlutterFragment instance(String tag){
        FlutterFragment fragment = new FlutterFragment();
//        fragment.setTabTag(tag);
        return fragment;
    }
}
