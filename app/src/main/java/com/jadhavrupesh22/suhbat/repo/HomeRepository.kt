package com.jadhavrupesh22.suhbat.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.jadhavrupesh22.suhbat.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
@Inject constructor(private val userCollection: CollectionReference, private val mAuth: FirebaseAuth) {

    suspend fun getAllUsers(): List<User> {
        return try {
            userCollection.get().await().toObjects(User::class.java)
        } catch (e: Exception) {
            Log.e(HomeRepository.TAG, "${e.message}")
            emptyList()
        }
    }

    companion object {
        const val TAG = "All Users"
    }


}