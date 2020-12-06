package com.jadhavrupesh22.suhbat.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.jadhavrupesh22.suhbat.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
@Inject constructor(
    private val userCollection: CollectionReference,
    private val mAuth: FirebaseAuth
) {

    var user:User = User()
    suspend fun getAllUsers(): List<User> {
        return try {
            userCollection.whereNotEqualTo("uid", mAuth.uid.toString()).get().await()
                .toObjects(User::class.java)
        } catch (e: Exception) {
            Log.e(HomeRepository.TAG, "${e.message}")
            emptyList()
        }
    }

    suspend fun getUser(): User? {
        return FirebaseFirestore.getInstance().collection("users").document(mAuth.uid.toString()).get().await().toObject(User::class.java)
    }


    companion object {
        const val TAG = "All Users"
    }


}