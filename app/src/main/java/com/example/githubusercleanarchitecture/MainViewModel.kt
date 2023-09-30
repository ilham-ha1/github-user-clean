package com.example.githubusercleanarchitecture

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
import com.example.core.domain.usecase.UserUseCase

class MainViewModel(private val userUseCase: UserUseCase): ViewModel(){
    private val usernameLiveData = MutableLiveData<String>()

    fun setUsername(username: String) {
        usernameLiveData.value = username
    }

    fun getUser(): LiveData<List<ItemsItem>> {
        return Transformations.switchMap(usernameLiveData) { username ->
            if (username.isBlank()) {
                userUseCase.getAllUser("").asLiveData()
            } else {
                userUseCase.getAllUser(username).asLiveData()
            }
        }
    }
}