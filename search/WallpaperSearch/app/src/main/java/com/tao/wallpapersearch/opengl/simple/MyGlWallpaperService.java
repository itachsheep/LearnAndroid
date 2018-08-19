package com.tao.wallpapersearch.opengl.simple;

import com.tao.wallpapersearch.L;
import com.tao.wallpapersearch.opengl.GLWallpaperService;


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
