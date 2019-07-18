package com.tao.wei.hybirdflutter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.taobao.idlefish.flutterboost.containers.BoostFlutterFragment;
import com.taobao.idlefish.flutterboost.interfaces.IFlutterViewContainer;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class FlutterFragment extends BoostFlutterFragment {
    public void setTabTag(String tag) {
        Bundle args = new Bundle();
        args.putString("tag",tag);
    }

    @Override
    public void onRegisterPlugins(PluginRegistry registry) {
        GeneratedPluginRegistrant.registerWith(registry);
    }

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
    public void destroyContainer() {

    }
    public static FlutterFragment instance(String tag){
        FlutterFragment fragment = null;//new FlutterFragment();
//        fragment.setTabTag(tag);
        return fragment;
    }
}
