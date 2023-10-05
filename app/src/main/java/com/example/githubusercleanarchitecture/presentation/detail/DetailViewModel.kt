package com.example.githubusercleanarchitecture.presentation.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.UserUseCase

class DetailViewModel(private val userUseCase: UserUseCase): ViewModel() {
    fun checkUser(id: Int) = userUseCase.checkUser(id)
    fun setFavorite(id: Int, username:String, avatarUrl:String)= userUseCase.setFavoriteUser(id, username, avatarUrl)
    fun removeFavorite(id:Int) =userUseCase.removeFavorite(id)
}