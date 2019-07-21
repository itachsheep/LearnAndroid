package test;

import android.content.Intent;
import android.os.Bundle;

import com.tao.wei.hybirdflutter.LogUtil;
import com.taobao.idlefish.flutterboost.containers.BoostFlutterActivity;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MyFlutterPage extends BoostFlutterActivity {
    private int id = 0;
    private String name;
    private String TAG = MyFlutterPage.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null) {
            String url = intent.getStringExtra("url");
            LogUtil.d(TAG,"onCreate url: " + url);
            /*Map map = UrlUtil.parseParams(url);
            id = Integer.parseInt(map.get("id").toString());
            name = map.get("name").toString();*/
        }
    }

    @Override
    public String getContainerName() {
        return "myFlutterPage";
    }

    @Override
    public Map getContainerParams() {
        LogUtil.d(TAG,"getContainerParams");
        Map<String,String> params = new HashMap<>();
        params.put("aaa","bbb");
        return params;
    }

    @Override
    public void onRegisterPlugins(PluginRegistry registry) {
        GeneratedPluginRegistrant.registerWith(registry);
    }
}
