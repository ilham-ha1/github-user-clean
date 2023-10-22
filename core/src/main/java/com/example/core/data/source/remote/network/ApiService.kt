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
    @Headers("Authorization: token ghp_EjNrmAU5zsbz${BuildConfig.API_KEY}iKehi2EraJq")
    suspend fun getSearchUsers(
        @Query("q") username: String
    ) : UserResponse

    @GET("users/{username}")
    @Headers("Authorization: token ghp_EjNrmAU5zsbz${BuildConfig.API_KEY}iKehi2EraJq")
    suspend fun getDetailUser(@Path("username") username: String): Response<DetailUserResponse>

}