package com.tao.wallpapersearch.opengl;
import android.opengl.GLSurfaceView;

import com.tao.wallpapersearch.L;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRender implements GLSurfaceView.Renderer {
    private String TAG = MyRender.class.getSimpleName();

    public MyRender(){
        super();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        L.i(TAG,"onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        L.i(TAG,"onSurfaceChanged");
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        L.i(TAG,"onDrawFrame");

        //绘制绿色背景
        gl.glClearColor(0.2f, 0.4f, 0.2f, 1f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

    }

    public void release(){

    }
}
