package hybird1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class Hybird1FlutterPageActivity extends AppCompatActivity {
    public static final String CHANNEL_NAME = "com.flutterbus/demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取由上一个页面传过来的routeName
        String routeName = "";
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            routeName = intent.getExtras().getString("routeName");
        }

        // 根据指定routeName创建FlutterView用来展示对应dart中的Widget
        FlutterView flutterView = Flutter.createView(this, this.getLifecycle(), routeName);

        // 创建Platform Channel用来和Flutter层进行交互
        new MethodChannel(flutterView, CHANNEL_NAME).setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                methodCall(methodCall, result);
            }
        });
        setContentView(flutterView);
    }

    /**
     * 处理dart层传来的方法调用
     */
    private void methodCall(MethodCall call, MethodChannel.Result result) {
        if (call.method.equals("gotoNativePage")) {
            startActivity(new Intent(this, Hybird1NativeActivity.class));
            result.success(true);
        } else {
            result.notImplemented();
        }
    }
}
