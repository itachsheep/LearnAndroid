package com.skyworth.base;

import android.os.Build;
import android.support.v4.app.FragmentActivity;

import com.skyworth.utils.L;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taowei on 2017/9/11.
 * check permission for sdk > 22 , Android 6.0 7.0
 * test
 */

public class CheckPermissionActivity extends FragmentActivity {
    private String tag = "CheckPermissionActivity";
    protected String[] needPermissions = new String[]
            {"android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.READ_PHONE_STATE"};

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions(this.needPermissions);
    }

    private void checkPermissions(String... permissions) {
        if(Build.VERSION.SDK_INT >= 23 && this.getApplicationInfo().targetSdkVersion >= 23) {
            List list = this.findDeniedPermissions(permissions);
            if(null != list && list.size() > 0) {
                try {
                    String[] res = (String[])list.toArray(new String[list.size()]);
                    Method method = this.getClass().getMethod("requestPermissions", new Class[]{String[].class, Integer.TYPE});
                    method.invoke(this, new Object[]{res, Integer.valueOf(0)});
                } catch (Throwable e) {
                    L.e(tag,"checkPermissions exception: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    private List<String> findDeniedPermissions(String[] permissions) {
        ArrayList list = new ArrayList();
        if(Build.VERSION.SDK_INT >= 23 && this.getApplicationInfo().targetSdkVersion >= 23) {
            String[] res = permissions;
            int length = permissions.length;

            for(int i = 0; i < length; ++i) {
                String per = res[i];
                if(this.checkMySelfPermission(per) != 0 || this.shouldShowMyRequestPermissionRationale(per)) {
                    list.add(per);
                }
            }
        }

        return list;
    }
    private int checkMySelfPermission(String pers) {
        try {
            Method method = this.getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer val = (Integer)method.invoke(this, new Object[]{pers});
            return val.intValue();
        } catch (Throwable e) {
            L.e(tag,"checkMySelfPermission exception: "+e.getMessage());
            return -1;
        }
    }
    private boolean shouldShowMyRequestPermissionRationale(String per) {
        try {
            Method method = this.getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean res = (Boolean)method.invoke(this, new Object[]{per});
            return res.booleanValue();
        } catch (Throwable e) {
            L.e(tag,"shouldShowMyRequestPermissionRationale exception: "+e.getMessage());
            return false;
        }
    }

}
