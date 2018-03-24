package com.skyworthauto.speak.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.skyworthauto.speak.SpeakApp;

public class GpsListener {

    private static final String TAG = GpsListener.class.getSimpleName();

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

    };

    public void requestLocationUpdates() {
        L.d(TAG, "requestLocationUpdates");
        LocationManager locationManager = getLocationManager();
        String locationProvider = getProvider();
        if (locationProvider != null) {
            locationManager
                    .requestLocationUpdates(locationProvider, 10000, 100.0f, mLocationListener);
        }
    }

    public void removeLocationUpdates() {
        L.d(TAG, "removeLocationUpdates");
        getLocationManager().removeUpdates(mLocationListener);
    }

    private String getProvider() {
        LocationManager locationManager = getLocationManager();

        String locationProvider = locationManager.getBestProvider(getCriteria(), true);
        if (null != locationProvider) {
            return locationProvider;
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        }
        return null;
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
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

    private LocationManager getLocationManager() {
        return (LocationManager) SpeakApp.getApp().getSystemService(Context.LOCATION_SERVICE);
    }
}
