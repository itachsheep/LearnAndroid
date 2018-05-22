package com.tao.memorysearch;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SDT14324 on 2018/5/21.
 */

public class TestGpsAc extends AppCompatActivity {
    private String TAG = "TestGpsAc";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

    }

    public void test_gps(View view){
//        long gpsTime = getGpsTime(TestGpsAc.this);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(100);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String res = sdf.format(mCalendar.getTime());
        Log.i(TAG,"res = "+res);
    }
    private MyLocationListener mLocationListener;
    private class  MyLocationListener implements LocationListener {
        private Context mContext;
        public MyLocationListener(Context context) {
            mContext = context;
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged location = "+location);
            if(mContext != null){
                ////1是自动 0是手动，自动 ，没网
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.setTimeInMillis(location.getTime());
                    Log.i(TAG, "onLocationChanged location time = "+location.getTime());
                    ((AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE)).setTime(location.getTime());

            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d(TAG,"onStatusChanged s = "+s);
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d(TAG,"onProviderEnabled s = "+s);
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d(TAG,"onProviderDisabled s = "+s);
        }
    };

    @SuppressLint("MissingPermission")
    public long getGpsTime(Context mContext){
        LocationManager mLocatManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (!mLocatManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(TAG,"GPS is not avaliable !!!");
            return 0;
        }
        String bestProvider = mLocatManager.getBestProvider(getCriteria(), true);
        Location location = mLocatManager.getLastKnownLocation(bestProvider);
        if(mLocationListener == null){
            mLocationListener = new MyLocationListener(mContext);
        }
        mLocatManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                1, mLocationListener);
        long time = location != null ? location.getTime():0;
        Log.d(TAG,"getGpsTime location time = "+time+", location = "+location);
        return time;
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
}
