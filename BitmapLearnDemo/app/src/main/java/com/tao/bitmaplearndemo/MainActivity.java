package com.tao.bitmaplearndemo;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //屏幕密度
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        //屏幕密度（像素比例：0.75, 1.0, 1.5, 2.0）
        float density = dm.density;
        //屏幕密度（每寸像素：120, 160, 240, 320）
        int densityDPI = dm.densityDpi;
        System.out.println(" density: "+density+", densityDPI: "+densityDPI);


        //BitmapFactory.Options options = new BitmapFactory.Options();
        //图片像素密度s
//        options.inDensity =

        //

        //

        //

        //BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background,options);
    }
}
