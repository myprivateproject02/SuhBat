package com.jadhavrupesh22.suhbat.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.repo.HomeRepository
import kotlinx.coroutines.*

class HomeViewModel
@ViewModelInject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    val TAG = HomeRepository.TAG

    private var _allUsers = MutableLiveData<List<User>>()
    val allUsers: LiveData<List<User>>
        get() = _allUsers

    private var _userDetails = MutableLiveData<User>()
    val userDetails:LiveData<User> get() = _userDetails



    init {
        viewModelScope.launch {
            _allUsers.postValue(homeRepository.getAllUsers())
            _userDetails.postValue(homeRepository.getUser())
        }





    }

}