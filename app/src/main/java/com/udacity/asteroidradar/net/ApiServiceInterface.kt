package com.udacity.asteroidradar.net

import com.udacity.asteroidradar.domain.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("api_key") apiKey: String,
        @Query("start_date") start: String,
        @Query("end_date") end: String
    ): String

    @GET("planetary/apod")
    suspend fun getImageDay(@Query("api_key") key: String): PictureOfDay
}