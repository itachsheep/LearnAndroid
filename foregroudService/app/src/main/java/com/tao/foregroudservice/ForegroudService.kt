package com.tao.foregroudservice

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.IBinder

/**
 * Created by SDT14324 on 2017/11/21.
 */
class ForegroudService : Service(){
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var builder = Notification.Builder(applicationContext)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_background))
                .setContentTitle("notification 标题")
                .setSmallIcon(R.mipmap.home_icon_day_prs)
                .setContentText("notification 内容")
        var notification = builder.build()
        startForeground(111,notification)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

}