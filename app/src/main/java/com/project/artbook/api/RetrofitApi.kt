package com.project.artbook.api

import com.project.artbook.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.project.artbook.util.Util.API_KEY
import retrofit2.Response

interface RetrofitApi {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = API_KEY,
    ) : Response<ImageResponse>
}