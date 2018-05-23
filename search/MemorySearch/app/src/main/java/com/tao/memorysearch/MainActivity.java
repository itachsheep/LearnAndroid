package com.tao.memorysearch;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(MainActivity.this,"chanelid");
        
        View view;

    }
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    Notification notification;

    public void send_notification(View view){

        builder.setContentText("通知内容");
        builder.setContentTitle("通知标题");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setTicker("滚动提示的文字...");
        notification  = builder.build();
        notificationManager.notify(R.mipmap.ic_launcher,notification);
    }

    public void remove_notification(View view){
        notificationManager.cancel(R.mipmap.ic_launcher);
    }

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observable<String> observable = new Observable<String>() {
        @Override
        protected void subscribeActual(Observer<? super String> observer) {

        }
    };

    Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {

        }
    });


    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };
}
