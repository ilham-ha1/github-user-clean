package com.example.favorite.presentation

import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.UserUseCase

class FavoriteViewModel(private val userUseCase: UserUseCase): ViewModel(){
    fun getFavorite(){
        userUseCase.getFavoriteUser()
    }
}