package com.tao.wallpapersearch.service;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.tao.wallpapersearch.L;
import com.tao.wallpapersearch.R;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;


public class CircleWallpaperService extends WallpaperService {
    private String TAG = CircleWallpaperService.class.getSimpleName();

    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    private class MyEngine extends Engine{
        private Handler mHandler = new Handler();
        private Runnable drawRunnable = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        private List<MyPoint> circles;
        private Paint mPaint = new Paint();
        private int width;
        private int height;
        private boolean visible = true;
        private boolean touchEnabled;
        private int maxNumber;

        public MyEngine(){
            SharedPreferences sharedPreferences = PreferenceManager.
                    getDefaultSharedPreferences(CircleWallpaperService.this);
            maxNumber = Integer.valueOf(sharedPreferences.
                    getString("numberOfCircles","4"));
            touchEnabled = sharedPreferences.getBoolean("touch",false);

            circles = new ArrayList<>();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeWidth(10f);

            mHandler.post(drawRunnable);
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            L.i(TAG,"onVisibilityChanged visible:"+visible);
            this.visible = visible;
            if(visible){
                mHandler.post(drawRunnable);
            }else {
                mHandler.removeCallbacks(drawRunnable);
            }
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            L.i(TAG,"onSurfaceDestroyed");
            this.visible = false;
            mHandler.removeCallbacks(drawRunnable);
        }


        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            L.i(TAG,"onSurfaceChanged format:"+format+",width: "+width+",height:"+height);
            this.width = width;
            this.height = height;
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if(touchEnabled){
                float eventX = event.getX();
                float eventY = event.getY();
                SurfaceHolder surfaceHolder = getSurfaceHolder();
                Canvas canvas = null;
                canvas = surfaceHolder.lockCanvas();
                if(canvas != null){
                    canvas.drawColor(Color.BLACK);
                    circles.clear();
                    circles.add(new MyPoint(String.valueOf(circles.size()+1),(int)eventX,
                            (int)eventY));
                    drawCircles(canvas,circles);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void draw(){
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            Canvas canvas = surfaceHolder.lockCanvas();
            if(canvas != null){
                if(circles.size() > maxNumber){
                    circles.clear();
                }

                int x = (int)(width * Math.random());
                int y = (int)(height * Math.random());

                circles.add(new MyPoint(String.valueOf(circles.size()+1),x,y));
                drawCircles(canvas,circles);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            mHandler.removeCallbacks(drawRunnable);
            if(visible){
                mHandler.postDelayed(drawRunnable,1000);
            }
        }

        private void drawCircles(Canvas canvas, List<MyPoint> list){
//            canvas.drawColor(Color.BLACK);
            canvas.drawColor(getResources().getColor(R.color.color_green_bg));
            for (MyPoint point:list) {
                canvas.drawCircle(point.x,point.y,20.0f,mPaint);
            }
        }
    }
}
