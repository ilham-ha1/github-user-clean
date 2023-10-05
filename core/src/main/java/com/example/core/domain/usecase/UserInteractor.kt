package com.example.core.domain.usecase

import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase{
    override fun getAllUser(username: String): Flow<List<ItemsItem>> {
        return userRepository.getAllUser(username)
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return userRepository.getFavoriteUser()
    }

    override fun setFavoriteUser(id: Int, username: String, avatarUrl: String) {
        return userRepository.setFavorite(id, username, avatarUrl)
    }

    override fun checkUser(id: Int): Int {
        return userRepository.checkUser(id)
    }

    override fun removeFavorite(id: Int): Int {
        return userRepository.removeFavorite(id)
    }


}