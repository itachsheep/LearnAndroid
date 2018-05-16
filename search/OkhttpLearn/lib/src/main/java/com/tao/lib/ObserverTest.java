package com.tao.lib;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by SDT14324 on 2018/3/15.
 */

public class ObserverTest {

    public static void main(java.lang.String[] args){
        Observer observer1 = new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                System.out.println("observer1 update is called , object: "+o.toString());
            }
        };

        Observer observer2 = new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                System.out.println("observer2 update is called, object: "+o.toString());
            }
        };

        Observable observable = new Observable();
        observable.addObserver(observer1);
        observable.addObserver(observer2);
//        observable.notifyObservers();
//
//        observer1.update(observable,"##changed##");
//        observable.notifyObservers();
        observable.hasChanged();
    }

}
