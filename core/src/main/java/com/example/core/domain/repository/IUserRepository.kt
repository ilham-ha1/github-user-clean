package com.example.core.domain.repository

import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllUser(username:String): Flow<List<User>>

    fun getFavoriteUser(): Flow<List<User>>

    fun setFavorite(id: Int, username:String, avatarUrl:String)

    fun checkUser(id:Int):Int

    fun removeFavorite(id:Int):Int
}