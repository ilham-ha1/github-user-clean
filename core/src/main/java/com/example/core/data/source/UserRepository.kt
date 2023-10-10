package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.core.utils.DataMapper

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): IUserRepository {
    override fun getAllUser(username: String): Flow<List<User>> {
        val data = remoteDataSource.getSearchUsers(username)
        val userList = data.map {
            DataMapper.mapItemToUser(it)
        }
        return userList
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

    override fun setFavorite(id: Int, username: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.addFavorite(id, username, avatarUrl)
        }
    }

    override fun checkUser(id: Int): Int {
        return localDataSource.check(id)
    }

    override fun removeFavorite(id: Int): Int {
        return localDataSource.removeFavorite(id)
    }

}