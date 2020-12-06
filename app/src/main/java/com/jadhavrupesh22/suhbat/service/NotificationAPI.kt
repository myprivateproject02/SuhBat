package com.jadhavrupesh22.suhbat.service

import com.jadhavrupesh22.suhbat.utils.Constant.CONTENT_TYPE
import com.jadhavrupesh22.suhbat.utils.Constant.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}