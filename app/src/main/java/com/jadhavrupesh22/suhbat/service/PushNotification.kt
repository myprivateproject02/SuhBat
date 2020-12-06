package com.jadhavrupesh22.suhbat.service

import com.jadhavrupesh22.suhbat.model.NotificationData

data class PushNotification(
    val data: NotificationData,
    val to: String
)