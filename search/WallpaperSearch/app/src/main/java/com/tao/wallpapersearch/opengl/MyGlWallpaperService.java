package com.tao.wallpapersearch.opengl;

import android.service.wallpaper.WallpaperService;

import com.tao.wallpapersearch.L;


public class MyGlWallpaperService extends GLWallpaperService {
    private String TAG = MyGlWallpaperService.class.getSimpleName();
    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    private class MyEngine extends GLEngine{
        MyRender myRender;
        public MyEngine(){
            super();
            myRender = new MyRender();
            setRenderer(myRender);
//            setRenderMode(RENDERMODE_CONTINUOUSLY);
            setRenderMode(RENDERMODE_WHEN_DIRTY);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            L.i(TAG,"onDestroy");
            if(myRender != null){
                myRender.release();
            }
            myRender = null;
        }
    }
}
