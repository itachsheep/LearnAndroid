package com.tao.foregroudservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by SDT14324 on 2017/12/1.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background))
                .setContentTitle("我的标题")
                .setSmallIcon(R.mipmap.home_icon_day_prs)
                .setContentText("我的内容");
        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(null,R.mipmap.home_icon_day_prs,notification);


        return super.onStartCommand(intent, flags, startId);
    }
}
