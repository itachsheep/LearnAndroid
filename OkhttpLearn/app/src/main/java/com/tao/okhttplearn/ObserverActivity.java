package com.tao.okhttplearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by SDT14324 on 2018/3/15.
 */

public class ObserverActivity extends AppCompatActivity {
    private String TAG = ObserverActivity.class.getSimpleName();
    MyObservable observable;
    Observer observer1;
    Observer observer2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        /*observer1 = new Observer() {
            @Override
            public void update(Observable observable, Object o) {

            }
        };

        observer2  = new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                Log.i(TAG,"observer2 update is called, object: "+o.toString());
            }
        };
        observable = new Observable();
        observable.addObserver(observer1);
        observable.addObserver(observer2);*/


        observer1 = new MyObserver();
        observer2 = new MyObserver();
        observable = new MyObservable();

        observable.addObserver(observer1);
        observable.addObserver(observer2);

    }

    public void changedNotify(View view){
        observable.setData(" 被观察者发生改变了！！");
    }

    class MyObserver implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            Log.i(TAG,"o : "+o+", arg: "+ arg);
        }
    }

    class MyObservable extends Observable{
        String data;
        public void setData(String dd){
            data = dd;
            setChanged();
            notifyObservers();
        }

    }
}
