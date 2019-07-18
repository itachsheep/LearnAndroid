package com.tao.wei.hybirdflutter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

import io.flutter.facade.Flutter;

public class MainActivity extends AppCompatActivity {
    public static WeakReference<MainActivity> sRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sRef = new WeakReference<>(this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View flutterView = Flutter.createView(
                        MainActivity.this,
                        getLifecycle(),
                        "route1"
                );
                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(600, 800);
                layout.leftMargin = 100;
                layout.topMargin = 200;
                addContentView(flutterView, layout);

                Intent intent = new Intent(MainActivity.this, FlutterDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("routeName", "first");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sRef.clear();
        sRef = null;
    }
}
