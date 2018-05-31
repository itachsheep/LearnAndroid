package com.tao.rxjavalearn.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.rxjavalearn.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import util.L;

/**
 * Created by SDT14324 on 2018/5/29.
 */

public class MapActivity extends AppCompatActivity {
    private String TAG = MapActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void map_network(View view){
        Observable
    }


    public void test_filter(View view){
        Observable.just("aaaa","bbb","cc")
        .filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                boolean res = s.contains("a");
                L.i(TAG,"Predicate test res = "+res);
                return res;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                L.i(TAG,"test_filter onNext s = "+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void test_flatmap(View view){
        Observable.just("aaaa","bbb","cc")
        .flatMap(new Function<String,Observable<Integer>>(){
            @Override
            public Observable<Integer> apply(String s) throws Exception {
                L.i(TAG,"test_flatmap flat map s = "+s);
                Integer[] info = new Integer[3];
                info[0] = s.length();
                info[1] = s.hashCode();
                info[2] = s.getBytes().length;
                return Observable.fromArray(info);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
               // L.i(TAG,"test_flatmap onSubscribe ");
            }

            @Override
            public void onNext(Integer integer) {
               // L.i(TAG,"test_flatmap onNext integer = "+integer);
            }

            @Override
            public void onError(Throwable e) {
                //L.i(TAG,"test_flatmap onError ");
            }

            @Override
            public void onComplete() {
              //  L.i(TAG,"test_flatmap onComplete ");
            }
        });
    }


    public void test_map(View view){
        Observable.just("aaaa","bbb","cc")
        .map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                L.i(TAG,"map apply s = "+s);
                return s == null ? 0 : s.length();
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                L.i(TAG,"onSubscribe ");
            }

            @Override
            public void onNext(Integer integer) {
               // L.i(TAG,"onNext  integer = "+integer);
            }

            @Override
            public void onError(Throwable e) {
               // L.i(TAG,"onError ");
            }

            @Override
            public void onComplete() {
               // L.i(TAG,"onComplete ");
            }
        });
    }
}
