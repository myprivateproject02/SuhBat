package com.jadhavrupesh22.suhbat.viewmodel

import androidx.annotation.NonNull
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.repo.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _isRegister = MutableLiveData<Boolean>()
    var isRegister: LiveData<Boolean> = _isRegister

    private var _isLogin = MutableLiveData<Boolean>()
    var isLogin: LiveData<Boolean> = _isLogin

    private var _isDataAdded = MutableLiveData<Boolean>()
    var isDataAdded: LiveData<Boolean> = _isDataAdded


    var isAuth: Boolean = isRegister.hasObservers()


    private var _allUsers = MutableLiveData<List<User>>()
    val allUsers: LiveData<List<User>>
        get() = _allUsers


    fun getUsers(): LiveData<List<User>> {
        viewModelScope.launch {
            _allUsers.postValue(authRepository.getData())
        }
        return allUsers
    }


    fun addUserData(user: User): LiveData<Boolean> {
        viewModelScope.launch {
            isDataAdded = authRepository.addUserData(user)
        }
        return isDataAdded
    }

    fun signUp(user: User): LiveData<Boolean> {
        viewModelScope.launch {
            isRegister = authRepository.signUp(user)
        }
        return isRegister
    }


    fun signIn(user: User): LiveData<Boolean> {
        viewModelScope.launch {
            isLogin = authRepository.signIn(user)
        }
        return isLogin
    }


}