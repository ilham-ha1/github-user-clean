package com.example.core.data.source.local

import com.example.core.data.source.local.entity.FavoriteUser
import com.example.core.data.source.local.room.FavoriteUserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteUserDao: FavoriteUserDao){
    fun getFavoriteUser(): Flow<List<FavoriteUser>> = favoriteUserDao.getFavoriteUser()

    fun addFavorite(favoriteUser: FavoriteUser) = favoriteUserDao.addFavorite(favoriteUser)

    fun removeFavorite(id: Int) = favoriteUserDao.removeFavorite(id)

    fun check(id: Int) = favoriteUserDao.check(id)
}