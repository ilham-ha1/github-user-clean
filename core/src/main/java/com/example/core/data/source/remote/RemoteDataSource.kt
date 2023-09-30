package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.DetailUserResponse
import com.example.core.data.source.remote.response.ItemsItem
import com.example.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {
    fun getSearchUsers(username: String): Flow<List<ItemsItem>> {
        return flow {
            try {
                val response = apiService.getSearchUsers(username)
                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    emit(dataArray)
                }
            } catch (e: Exception) {
                Log.e("Remote DataSource getSearchUsers", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailUser(username: String): Flow<ApiResponse<DetailUserResponse>>{
        return flow<ApiResponse<DetailUserResponse>> {
            try{
                val response = apiService.getDetailUser(username)
                val data = response.body()
                if(data != null){
                    emit(ApiResponse.Success(data))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote DataSource getDetailUser", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}