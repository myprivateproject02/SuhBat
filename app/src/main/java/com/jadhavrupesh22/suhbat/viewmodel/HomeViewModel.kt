package com.jadhavrupesh22.suhbat.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadhavrupesh22.suhbat.model.User
import com.jadhavrupesh22.suhbat.repo.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel
@ViewModelInject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    private var _allUsers = MutableLiveData<List<User>>()
    val allUsers: LiveData<List<User>> = _allUsers

    init {
        viewModelScope.launch {
            _allUsers.postValue(homeRepository.getAllUsers())
        }
    }

}