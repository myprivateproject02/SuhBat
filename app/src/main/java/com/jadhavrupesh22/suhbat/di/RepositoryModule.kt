package com.jadhavrupesh22.suhbat.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.jadhavrupesh22.suhbat.repo.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

//    @Singleton
//    @Provides
//    fun provideHomeRepository(userCollection: CollectionReference, mAuth: FirebaseAuth) =
//        HomeRepository(userCollection, mAuth)
//
//    @Singleton
//    @Provides
//    fun provideAuthRepository(userCollection: CollectionReference, mAuth: FirebaseAuth) =
//        HomeRepository(userCollection, mAuth)



}