package com.example.core.domain.usecase

import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase{
    fun getAllUser(username:String): Flow<List<User>>
    fun getFavoriteUser(): Flow<List<User>>
    fun setFavoriteUser(id: Int, username: String, avatarUrl:String)
    fun checkUser(id:Int): Int
    fun removeFavorite(id:Int):Int
}