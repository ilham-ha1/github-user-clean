package com.example.githubusercleanarchitecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.UserUseCase

class MainViewModel(private val userUseCase: UserUseCase): ViewModel(){
    private val usernameLiveData = MutableLiveData<String>()

    fun setUsername(username: String) {
        usernameLiveData.value = username
    }

    fun getUser() = Transformations.switchMap(usernameLiveData) { username ->
        if (username.isBlank()) {
            userUseCase.getAllUser("").asLiveData()
        } else {
            userUseCase.getAllUser(username).asLiveData()
        }
    }
}