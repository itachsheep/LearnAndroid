package com.tao.weexsearch.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
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

/**
 * Created by taowei on 2018/6/6.
 * 2018-06-06 22:47
 * WeexSearch_bak
 * com.tao.weexsearch.activity
 */

public class TestMyModuleActivity extends AppCompatActivity implements IWXRenderListener {
    private LinearLayout linearLayout;
    private String TAG = TestMyModuleActivity.class.getSimpleName();
    private WXSDKInstance mWxsdkInstance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        linearLayout = (LinearLayout) findViewById(R.id.ll_container);
        mWxsdkInstance = new WXSDKInstance(this);
        mWxsdkInstance.registerRenderListener(this);
        Log.i(TAG,"onCreate ");
        mWxsdkInstance.render("TestModule",
                WXFileUtils.loadAsset("testmodule.js", this),
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
