package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.source.local.entity.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): Flow<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun removeFavorite(id: Int):Int

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun check(id: Int): Int
}