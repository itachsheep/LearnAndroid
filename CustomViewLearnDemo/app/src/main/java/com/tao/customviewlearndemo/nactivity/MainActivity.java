package com.tao.customviewlearndemo.nactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.customviewlearndemo.R;
import com.tao.customviewlearndemo.activity.AnimateActivity;
import com.tao.customviewlearndemo.activity.C30Activity;
import com.tao.customviewlearndemo.activity.C30Activity2;
import com.tao.customviewlearndemo.activity.SectorViewActivity;
import com.tao.customviewlearndemo.activity.ViewTestActivity;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int densityDpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;
        Log.i("Main","densityDpi = "+densityDpi+", density = "+density
        +", widht = "+displayMetrics.widthPixels+", height = "+displayMetrics.heightPixels);*/
    }

    public void toSectorView(View view){
        startActivity(new Intent(MainActivity.this, SectorViewActivity.class));
    }

    public void toCirleRangeView(View view){
        startActivity(new Intent(MainActivity.this, ViewTestActivity.class));
    }

    public void toNormalCircle(View view){
        startActivity(new Intent(MainActivity.this,C30Activity.class));
    }

    public void toAnimate(View view ){
        startActivity(new Intent(MainActivity.this,AnimateActivity.class));
    }

    public void toCActivity(View view){
        startActivity(new Intent(MainActivity.this,C30Activity2.class));
    }

    public void toLightDelay(View view){
        startActivity(new Intent(MainActivity.this, LightDelayActivity.class));
    }

    public void toAtmosphere(View view){
        startActivity(new Intent(MainActivity.this,AtmosActivity.class));
    }

    public void toSwitch(View view){
        startActivity(new Intent(MainActivity.this, SwitchActivity.class));
    }
}
