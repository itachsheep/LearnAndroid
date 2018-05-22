package com.tao.rxjavalearn.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by SDT14324 on 2018/5/22.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        };

        ObservalbeT observalbeT = new ObservalbeT();
        observalbeT.addObserver(observer);

    }

    class ObservalbeT extends Observable {
        String data;
        public void setData(String dd){
            data = dd;
            setChanged();
            notifyObservers();
        }

        @Override
        public void notifyObservers() {
            notifyObservers(data);
        }
    }


}
