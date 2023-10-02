package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.FavoriteUser
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): IUserRepository {
    override fun getAllUser(username: String): Flow<List<ItemsItem>> {
        return remoteDataSource.getSearchUsers(username)
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUser().map { favoriteUsers ->
            favoriteUsers.map { favoriteUser ->
                User(
                    id = favoriteUser.id,
                    username = favoriteUser.username,
                    avatarUrl = favoriteUser.avatarUrl
                )
            }
        }
    }

    override fun setFavorite(favoriteUser: FavoriteUser) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.addFavorite(favoriteUser)
        }
    }

    override fun checkUser(id: Int): Int {
        return localDataSource.check(id)
    }

    override fun removeFavorite(id: Int): Int {
        return localDataSource.removeFavorite(id)
    }

}