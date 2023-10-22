package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.DetailUserResponse
import com.example.core.data.source.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.core.BuildConfig

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getSearchUsers(
        @Query("q") username: String
    ) : UserResponse

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getDetailUser(@Path("username") username: String): Response<DetailUserResponse>

}