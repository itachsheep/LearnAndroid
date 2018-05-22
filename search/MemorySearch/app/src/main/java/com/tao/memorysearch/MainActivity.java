package com.tao.memorysearch;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
}
