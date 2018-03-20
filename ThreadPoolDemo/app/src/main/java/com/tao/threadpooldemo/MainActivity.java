package com.tao.threadpooldemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        int CORE_POOL_SIZE = 10;
        Executors.newScheduledThreadPool(CORE_POOL_SIZE);


        fixedThreadPool.submit(new Runnable() {
            @Override
            public void run() {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume "+System.currentTimeMillis());

        final TextView tv = findViewById(R.id.tv_test);
        ViewTreeObserver viewTreeObserver = tv.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float textSize = tv.getTextSize();
                Log.i(TAG,"onGlobalLayout "+System.currentTimeMillis());
                float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
                Log.i(TAG,"scaledDensity = "+scaledDensity+", textSize = "+textSize);
                Log.i(TAG,"tv px = "+ (textSize * scaledDensity + 0.5f));
                Log.i(TAG,"tv width = "+tv.getWidth());
            }
        });
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
