package com.jadhavrupesh22.suhbat.di

import com.jadhavrupesh22.suhbat.service.NotificationAPI
import com.jadhavrupesh22.suhbat.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object FCMModule {


    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(1200, TimeUnit.SECONDS)
        .connectTimeout(1200, TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun provideNotificationAPI(): NotificationAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NotificationAPI::class.java)
    }


}