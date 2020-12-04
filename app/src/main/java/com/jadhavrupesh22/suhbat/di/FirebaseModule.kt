package com.jadhavrupesh22.suhbat.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jadhavrupesh22.suhbat.R
import com.jadhavrupesh22.suhbat.utils.Constant
import com.jadhavrupesh22.suhbat.utils.Constant.USERS_COLLECTION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object FirebaseModule {



    @Singleton
    @Provides
    fun provideFireStore(): CollectionReference = Firebase.firestore.collection(USERS_COLLECTION)

    @Singleton
    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()


    @Singleton
    @Provides
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()




}