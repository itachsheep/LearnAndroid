package com.tao.wallpapersearch.service;

import android.content.Context;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.tao.wallpapersearch.L;
import com.tao.wallpapersearch.engine.MySurfaceView;

public class LiveWallpaperService extends WallpaperService {
    private Context context;
    private MyEngine myEngine;
    private final static long REFLESH_GAP_TIME = 1000L;//如果想播放的流畅，需满足1s 16帧   62ms切换间隔时间


    private String TAG = LiveWallpaperService.class.getSimpleName();


    @Override
    public Engine onCreateEngine() {
        L.i(TAG,"onCreateEngine");
        this.context = this;
        this.myEngine = new MyEngine();
        return this.myEngine;
    }



    private class MyEngine extends LiveWallpaperService.Engine{
        private final SurfaceHolder surfaceHolder;
        private MySurfaceView mySurfaceView;
        private Handler handler;
        private Runnable viewRunnable;


        public MyEngine(){
            L.i(TAG,"MyEngine()");
            this.surfaceHolder = getSurfaceHolder();
            this.mySurfaceView = new MySurfaceView(LiveWallpaperService.this.getBaseContext());
            this.mySurfaceView.initView(surfaceHolder);
            this.handler = new Handler();
            this.initRunnable();
            this.handler.post(this.viewRunnable);
        }

        private void initRunnable() {
            this.viewRunnable = new Runnable() {
                @Override
                public void run() {
                    MyEngine.this.drawView();
                }
            };
        }

        private void drawView() {
            if (this.mySurfaceView == null) {
                return;
            } else {
                this.handler.removeCallbacks(this.viewRunnable);
                this.mySurfaceView.surfaceChanged(this.surfaceHolder, -1, this.mySurfaceView.getWidth(),
                        this.mySurfaceView.getHeight());
                if (!(isVisible())) {
                    return;
                } else {
                    this.handler.postDelayed(this.viewRunnable, REFLESH_GAP_TIME);
                    this.mySurfaceView.loadNextWallpaperBitmap();
                }
            }
        }


        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            L.i(TAG,"onSurfaceCreated holder:"+holder);
            this.drawView();
            if (this.mySurfaceView != null) {
                this.mySurfaceView.surfaceCreated(holder);
            } else {
                //nothing
            }
        }


        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            L.i(TAG,"onSurfaceChanged");
            this.drawView();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            L.i(TAG,"onVisibilityChanged visible: "+visible);
            if (this.handler != null) {
                if (visible) {
                    this.handler.post(this.viewRunnable);
                } else {
                    this.handler.removeCallbacks(this.viewRunnable);
                }
            } else {
                //nothing
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            L.i(TAG,"onSurfaceDestroyed ");
            if (this.handler != null) {
                this.handler.removeCallbacks(this.viewRunnable);
            } else {
                //nothing
            }
            if (this.mySurfaceView != null) {
                this.mySurfaceView.surfaceDestroyed(holder);
            } else {
                //nothing
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            L.i(TAG,"onDestroy ");
            if (this.handler != null) {
                this.handler.removeCallbacks(this.viewRunnable);
            } else {
                //nothing
            }
        }
    }
}
