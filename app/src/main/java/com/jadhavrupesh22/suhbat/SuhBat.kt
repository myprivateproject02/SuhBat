package com.jadhavrupesh22.suhbat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.jadhavrupesh22.suhbat.utils.Constant.CHANNEL_ID
import com.jadhavrupesh22.suhbat.utils.Constant.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SuhBat : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            val mNotifyManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotifyManager.createNotificationChannel(channel)
        }
    }

}