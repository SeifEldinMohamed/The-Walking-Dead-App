package com.seif.thewalkingdeadapp.data.remote

import com.seif.thewalkingdeadapp.data.remote.dto.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheWalkingDeadApi {
    @GET("/heroes")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/character/search")
    suspend fun searchHeroes(
        @Query("name") name: String
    ): ApiResponse
}