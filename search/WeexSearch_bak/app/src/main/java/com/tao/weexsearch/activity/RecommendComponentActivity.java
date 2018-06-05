package com.tao.weexsearch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.tao.weexsearch.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

public class RecommendComponentActivity extends AppCompatActivity implements IWXRenderListener {
    private LinearLayout linearLayout;
    private String TAG = RecommendComponentActivity.class.getSimpleName();
    private WXSDKInstance mWxsdkInstance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        linearLayout = (LinearLayout) findViewById(R.id.ll_container);
        mWxsdkInstance = new WXSDKInstance(this);
        mWxsdkInstance.registerRenderListener(this);
        Log.i(TAG,"onCreate ");
        /**
         * Render template asynchronously
         *
         * @param pageName, used for performance log.
         * @param template bundle js
         * @param options  os   iphone/android/ipad
         *                 weexversion    Weex version(like 1.0.0)
         *                 appversion     App version(like 1.0.0)
         *                 devid        Device id(like Aqh9z8dRJNBhmS9drLG5BKCmXhecHUXIZoXOctKwFebH)
         *                 sysversion    Device system version(like 5.4.4、7.0.4, should be used with os)
         *                 sysmodel     Device model(like iOS:"MGA82J/A", android:"MI NOTE LTE")
         *                 Time    UNIX timestamp, UTC+08:00
         *                 TTID(Optional)
         *                 MarkertId
         *                 Appname(Optional)  tm,tb,qa
         *                 Bundleurl(Optional)  template url
         * @param jsonInitData Initial data for rendering
         * @param flag     RenderStrategy {@link WXRenderStrategy}
         */
        mWxsdkInstance.render("WeexListApp",
                    WXFileUtils.loadAsset("recommendcomponent.js", this),
                        null,
                        null,
                        WXRenderStrategy.APPEND_ASYNC);

    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.i(TAG,"onViewCreated ");
        linearLayout.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.i(TAG,"onRenderSuccess ");
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.i(TAG,"onRefreshSuccess ");
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.i(TAG,"onException "+", errcod = "+errCode+", msg = "+msg);
    }
}
