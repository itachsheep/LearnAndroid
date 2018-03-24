package com.tao.customviewlearndemo.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by SDT14324 on 2018/1/16.
 */

public class _3DAnimation extends Animation {
    private  float mFromDegrees;
    private  float mToDegrees;
    private  float mCenterX;
    private  float mCenterY;
    private Camera mCamera;
    public  _3DAnimation(float fromDegress,float toDegress){
        this.mFromDegrees=fromDegress;
        this.mToDegrees=toDegress;

    }
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.mCenterX=width/2;
        this.mCenterY=height/2;
        mCamera=new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + (mToDegrees - mFromDegrees) * interpolatedTime;
        final Matrix matrix = t.getMatrix();
        //interpolatedTime 0~1变化
        mCamera.save();
        mCamera.rotateY(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-mCenterX, -mCenterY);//相机位于（0,0），移动图片，相机位于图片中心
        matrix.postTranslate(mCenterX, mCenterY);
    }
}
