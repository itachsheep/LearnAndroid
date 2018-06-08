package com.tao.weexsearch;

import android.app.Application;
import android.util.Log;

import com.tao.weexsearch.components.CircleImageView;
import com.tao.weexsearch.components.RefreshView;
import com.tao.weexsearch.module.CommonModule;
import com.tao.weexsearch.module.MyModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.ui.SimpleComponentHolder;

import java.awt.font.TextAttribute;

/**
 * Created by SDT14324 on 2018/5/31.
 */

public class WXApplication extends Application {
    private static WXApplication weexListApp;
    private String TAG = WXApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        weexListApp = this;
        try {
            InitConfig config = new InitConfig.Builder().setImgAdapter(new WeexImageAdapter()).build();
            WXSDKEngine.registerComponent("circleImageView", CircleImageView.class);
            WXSDKEngine.registerComponent(new SimpleComponentHolder(RefreshView.class,
                            new RefreshView.Ceator())
                    , false, "refreshview");

            WXSDKEngine.registerModule("commonmodule", CommonModule.class);

            WXSDKEngine.registerModule("MyModule", MyModule.class);

            WXSDKEngine.initialize(this, config);

        }catch (Exception e){

        }
        Log.i(TAG,"oncreate finish");
    }

    public static WXApplication getApp() {
        return weexListApp;
    }
}
