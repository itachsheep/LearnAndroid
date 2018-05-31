package com.tao.rxjavalearn.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.rxjavalearn.R;

import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import util.L;

public class RxObserverActivity extends AppCompatActivity {
    private String TAG = RxObserverActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Socket socket = null;

    }
    Observable<String> observable1;
    Observable<String> observable2;
    Observable<String> observable3;
    Observable<String> observable4;
    public void test_init(View view){
        //第一种方式：普通观察者模式
        observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                L.i(TAG,"ObservableEmitter subscribe emitter = "+emitter);
                emitter.onNext("haha1");
                emitter.onNext("haha2");
                emitter.onNext("haha3");
                emitter.onComplete();
            }
        });

        //第二种方式：采用数组的方式
        observable2 = Observable.just("11111","2222","33333","44444"
        ,"5555","6666","77777","8888","9999","10aaaa");
        //第二种方式：采用数组的方式
        String[] words = {"aaaa", "bbbb", "cccc"};
        observable3 = Observable.fromArray(words);


    }

    public void test_commit_action(View view){
        //第三种方式：提交任务
        String[] words = {"aaaa", "bbbb", "cccc"};
        observable4 = Observable.fromArray(words);
        observable4.subscribe(onNext,onError,onComplete);
    }

    public void test_observer(View view){
        //第一种方式：普通观察者模式
//        observable1.subscribe(new MyObserver<String>());
        //第二种方式：采用数组的方式
//        observable2.subscribe(new MyObserver<String>());
        //第二种方式：采用数组的方式
        L.i(TAG,"test_observer thread : "+Thread.currentThread().getName());
        observable3.subscribe(new MyObserver<String>());
    }



    Consumer<String> onNext = new Consumer<String>() {
        @Override
        public void accept(String s) throws Exception {
            L.i(TAG,"onNext accept s = "+s+", thread = "+Thread.currentThread().getName());
        }
    };

    Consumer<Throwable> onError = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception {
            L.i(TAG,"onError throwable  = "+throwable+", thread = "+Thread.currentThread().getName());
        }
    };

    Action onComplete = new Action() {
        @Override
        public void run() throws Exception {
            L.i(TAG,"onComplete run "+", thread = "+Thread.currentThread().getName());
        }
    };



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
