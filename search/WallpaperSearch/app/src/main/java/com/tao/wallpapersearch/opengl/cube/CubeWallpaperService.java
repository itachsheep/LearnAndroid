package com.tao.wallpapersearch.opengl.cube;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.tao.wallpapersearch.L;
import com.tao.wallpapersearch.opengl.GLWallpaperService;


public class CubeWallpaperService extends GLWallpaperService {
    private String TAG = CubeWallpaperService.class.getSimpleName();
    @Override
    public Engine onCreateEngine() {
        return new CubeEngine();
    }

    private class CubeEngine extends GLEngine{
        CubeRender cubeRender;
        public CubeEngine(){
            super();
            cubeRender = new CubeRender(CubeWallpaperService.this);
            setRenderer(cubeRender);
//            setRenderMode(RENDERMODE_WHEN_DIRTY);
            setRenderMode(RENDERMODE_CONTINUOUSLY);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            L.i(TAG,"onCreate");
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            cubeRender.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if(cubeRender != null){
                cubeRender.release();
            }
            cubeRender = null;
        }
    }
}
