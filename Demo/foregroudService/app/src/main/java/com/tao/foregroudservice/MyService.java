package com.tao.foregroudservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by SDT14324 on 2017/12/1.
 */

public class MyService extends Service {
    private String TAG = MyService.class.getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand show notification!!");
        NotificationManager notificationManager =
                (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent action = new Intent(this,TestActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                action,PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background))
                .setContentTitle("我的标题")
                .setSmallIcon(R.mipmap.home_icon_day_prs)
                .setContentText("我的内容")
//                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
            ;
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        notificationManager.notify(R.mipmap.home_icon_day_prs,notification);


        return super.onStartCommand(intent, flags, startId);
    }
}
