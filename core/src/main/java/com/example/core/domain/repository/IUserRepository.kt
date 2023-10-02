package com.example.core.domain.repository

import com.example.core.data.source.local.entity.FavoriteUser
import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllUser(username:String): Flow<List<ItemsItem>>

    fun getFavoriteUser(): Flow<List<User>>

    fun setFavorite(favoriteUser: FavoriteUser)

    fun checkUser(id:Int):Int

    fun removeFavorite(id:Int):Int
}