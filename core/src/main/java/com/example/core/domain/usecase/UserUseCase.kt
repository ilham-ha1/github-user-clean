package com.example.core.domain.usecase

import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase{
    fun getAllUser(username:String): Flow<List<ItemsItem>>
    fun getFavoriteUser(): Flow<List<User>>
    fun setFavoriteUser(id: Int, username: String, avatarUrl:String)
    fun checkUser(id:Int): Int
    fun removeFavorite(id:Int):Int
}