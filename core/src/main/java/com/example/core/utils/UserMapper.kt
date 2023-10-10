package com.example.core.utils

import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.domain.model.User
object DataMapper {
    fun mapItemToUser(input: List<ItemsItem>): List<User> =
        input.map {
            User(
                id = it.id,
                username = it.login,
                avatarUrl = it.avatarUrl
            )
    }
}