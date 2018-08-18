package com.tao.wallpapersearch;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tao.wallpapersearch.service.LiveWallpaperService;

import java.io.IOException;

public class WallpaperActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    WallpaperManager mWallpaperManager;
    String TAG = WallpaperActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        mWallpaperManager = WallpaperManager.getInstance(this);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private int i = 0;
    public void set_static_wallpaper(View view){
        i++;
        try {
            if(i % 2 == 0){
                //使用Bitmap设置壁纸
                //     * 直接设置为壁纸，不会有任何界面和弹窗出现
                //     * 壁纸切换，会有动态的渐变切换
                L.i(TAG,"set_static_wallpaper setBitmap");
                mWallpaperManager.setBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.wallpaper));
            }else {
                //使用资源文件设置壁纸
                //     * 直接设置为壁纸，不会有任何界面和弹窗出现
                L.i(TAG,"set_static_wallpaper setResource");
                mWallpaperManager.setResource(R.raw.girl);
            }
            showToast("设置静态壁纸成功");
        } catch (IOException e) {
            L.i(TAG,"set_static_wallpaper e:"+e);
            showToast("设置静态壁纸失败");
            e.printStackTrace();
        }
    }

    public void clear_wallpaper(View view){
        try {
            mWallpaperManager.clear();
            showToast("清除静态壁纸成功");
        } catch (IOException e) {
            L.i(TAG,"clear_wallpaper e:"+e);
            showToast("清除静态壁纸失败");
            e.printStackTrace();
        }
    }

    private void showToast(String mes){
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();
    }


    private final static int REQUEST_CODE_SET_WALLPAPER = 0x001;
    public void set_dynamic_wallpaper(View view){
        Intent intent = new Intent();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(getPackageName(), LiveWallpaperService.class.getCanonicalName()));
        }else {
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
        //WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER;

        startActivityForResult(intent,REQUEST_CODE_SET_WALLPAPER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.i(TAG,"onActivityResult requestCode:"+requestCode+", resultCode:"+resultCode
        +", data:"+data);
        if(requestCode == REQUEST_CODE_SET_WALLPAPER){
            if(resultCode == RESULT_OK){
                showToast("设置动态壁纸成功");
            }else {
                showToast("设置动态壁纸失败");
            }

        }else {

        }
    }
}
