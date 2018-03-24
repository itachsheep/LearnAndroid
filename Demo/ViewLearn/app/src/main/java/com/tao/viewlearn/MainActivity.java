package com.tao.viewlearn;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import beans.SparseBean;

public class MainActivity extends AppCompatActivity {
    private Button bt2;
    private Button bt1;
    private ImageView iv;
    private ViewStub viewStub;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            iv.invalidate();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);

        initView();
        initListener();
//        testSparseArray();


    }



    private void initListener() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* LogUtil.i("left: "+bt2.getLeft()+ ", top : "+bt2.getTop()+ ", x: "+bt2.getX()+ ", translationX: "+bt2.getTranslationX()+ ", pivotX: "+bt2.getPivotX());
                bt2.scrollTo(bt2.getLeft() * 2,bt2.getTop() * 2);*/

               //补间动画
                /*Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha_anim);
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate_anim);
                iv.startAnimation(animation);*/

                //属性动画
                /*ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"rotation",0,360);
                animator.setDuration(3000);
                animator.start();

                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(iv, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
                ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(iv, "scaleX", 0.0f, 1.0f);
                ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(iv, "scaleY", 0.0f, 2.0f);
                ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(iv, "rotation", 0, 360);
                ObjectAnimator transXAnim = ObjectAnimator.ofFloat(iv, "translationX", 100, 400);
                ObjectAnimator transYAnim = ObjectAnimator.ofFloat(iv, "translationY", 100, 750);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
                set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
                set.setDuration(3000);
                set.start();*/


                //正弦函数
                /*Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.object_translate);
                iv.startAnimation(animation);*/


            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("MainActivity.dispatchTouchEvent ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("MainActivity.onTouchEvent ");
        return super.onTouchEvent(event);
    }


    private void postInvalidate() {
        mHandler.sendEmptyMessage(1);
    }


    private void initView() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        iv = (ImageView) findViewById(R.id.iv);
        viewStub = (ViewStub) findViewById(R.id.viewstub);

    }

    private void testSparseArray() {
        SparseArray<SparseBean> sparseArray = new SparseArray<>();
        for(int i = 0; i < 10; i++){
            sparseArray.put(i,new SparseBean(i,i+""));
        }

        for (int j = 0; j < sparseArray.size(); j++){
            LogUtil.i(j+ " ---- "+sparseArray.get(j).toString());
        }
    }
}
