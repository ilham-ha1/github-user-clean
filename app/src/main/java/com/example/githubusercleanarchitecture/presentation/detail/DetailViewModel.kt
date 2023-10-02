package com.example.githubusercleanarchitecture.presentation.detail

import androidx.lifecycle.ViewModel
import com.example.core.data.source.local.entity.FavoriteUser
import com.example.core.domain.usecase.UserUseCase

class DetailViewModel(private val userUseCase: UserUseCase): ViewModel() {
    fun checkUser(id: Int) = userUseCase.checkUser(id)
    fun setFavorite(favoriteUser: FavoriteUser)= userUseCase.setFavoriteUser(favoriteUser)
    fun removeFavorite(id:Int) =userUseCase.removeFavorite(id)
}