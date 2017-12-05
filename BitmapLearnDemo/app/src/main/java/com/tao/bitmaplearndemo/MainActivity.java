package com.tao.bitmaplearndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //屏幕密度
       // DisplayMetrics dm = new DisplayMetrics();
        DisplayMetrics  dm = getResources().getDisplayMetrics();

        //屏幕密度（像素比例：0.75, 1.0, 1.5, 2.0）
        float density = dm.density;
        //屏幕密度（每寸像素：120, 160, 240, 320）
        int densityDPI = dm.densityDpi;
        System.out.println(" density: "+density+", densityDPI: "+densityDPI);
        System.out.println(" dm.widthPixel: "+dm.widthPixels+", dm.heightPixel:  "+dm.heightPixels);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        System.out.println(" metrics.widthPixel: "+metrics.widthPixels+", metrics.heightPixel: "+metrics.heightPixels);
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //图片像素密度s
//        options.inDensity =
        //BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background,options);
    }
}
