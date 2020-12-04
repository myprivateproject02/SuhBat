package com.jadhavrupesh22.suhbat.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.jadhavrupesh22.suhbat.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository
@Inject constructor(
    private val mAuth: FirebaseAuth,
    private val userCollection: CollectionReference
) {
    private val TAG = "AuthRepository"
    var isAuth = false
    private var _isRegister = MutableLiveData<Boolean>()
    var isRegistered: LiveData<Boolean> = _isRegister


    private var _isDataAdded = MutableLiveData<Boolean>()
    var isDataAdded: LiveData<Boolean> = _isDataAdded


    suspend fun getData(): List<User> {
        return try {
            userCollection.get().await().toObjects(User::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            emptyList()
        }
    }


    suspend fun signUp(user: User): LiveData<Boolean> {
        mAuth.createUserWithEmailAndPassword(user.emailId, user.password)
            .addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isComplete) {
                        _isRegister.value = true
                    } else {
                        _isRegister.value = false
                    }
                })
        return isRegistered

    }

    suspend fun addUserData(user: User): LiveData<Boolean> {
        userCollection.document(mAuth.uid.toString()).set(user)
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    _isDataAdded.value = true
                } else {
                    _isDataAdded.value = false
                }

            })
        return isDataAdded

    }

    suspend fun signIn(user: User): LiveData<Boolean> {
        mAuth.signInWithEmailAndPassword(user.emailId, user.password)
            .addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isComplete) {
                        _isRegister.value = true
                    } else {
                        _isRegister.value = false
                    }
                })
        return isRegistered

    }


}