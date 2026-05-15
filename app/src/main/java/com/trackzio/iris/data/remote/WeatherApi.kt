package com.trackzio.iris.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/search")
    suspend fun searchCities(
        @Query("name") city: String,
        @Query("count") count: Int = 5
    ): CityResponse
}