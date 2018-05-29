package com.tao.rxjavalearn.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tao.rxjavalearn.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SDT14324 on 2018/5/25.
 */

public class SchedulerActivity extends AppCompatActivity {
    private String TAG = SchedulerActivity.class.getSimpleName();
    private String imageurl = "https://mobile.umeng.com/images/pic/home/social/img-1.png";
    private ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        iv = (ImageView) findViewById(R.id.sche_iv);
    }

    public void test_normal(View view){
        //第一种方式：普通观察者模式
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                L.i(TAG,"ObservableEmitter subscribe emitter = "+emitter);
                emitter.onNext("haha1");
                emitter.onNext("haha2");
                emitter.onNext("haha3");
                emitter.onComplete();
            }
        });
        //第一种方式：普通观察者模式
        L.i(TAG,"test_norml observalbe1 = "+observable1);
        observable1.subscribe(new MyObserver<String>());
    }
    public void test_scheduer(View view){
        Observer<Bitmap> observer = new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {
                L.i(TAG,"test_scheduer onSubscribe"+", thread = "+Thread.currentThread().getName());
            }

            @Override
            public void onNext(Bitmap bitmap) {
                L.i(TAG,"test_scheduer onNext bitmap == null ? "+(bitmap == null)+", thread = "+Thread.currentThread().getName());

                if(bitmap == null )return ;
                iv.setImageBitmap(bitmap);
            }

            @Override
            public void onError(Throwable e) {
                L.i(TAG,"test_scheduer onError"+", thread = "+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                L.i(TAG,"test_scheduer onComplete"+", thread = "+Thread.currentThread().getName());
            }
        };
        Observable<Bitmap> observable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                L.i(TAG,"subscribe "+", thread : "+Thread.currentThread().getName());
                Bitmap bitmap = L.getBitmap(imageurl);
                emitter.onNext(bitmap);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())//subscribe()执行的线程
                .observeOn(AndroidSchedulers.mainThread());//指定 observer 的回调发生在主线程
        L.i(TAG,"test_scheduer observable = "+observable);
        observable.subscribe(observer);
    }

    class MyObserver<String> implements Observer<String> {
        @Override
        public void onSubscribe(Disposable d) {
            L.i(TAG,"onSubscribe d = "+d+", thread : "+Thread.currentThread().getName());
        }

        @Override
        public void onNext(String s) {
            L.i(TAG,"onNext s = "+s+", thread : "+Thread.currentThread().getName());

        }

        @Override
        public void onError(Throwable e) {
            L.i(TAG,"onError  "+", thread : "+Thread.currentThread().getName());
        }

        @Override
        public void onComplete() {
            L.i(TAG,"onComplete"+", thread : "+Thread.currentThread().getName());
        }
    };
}
