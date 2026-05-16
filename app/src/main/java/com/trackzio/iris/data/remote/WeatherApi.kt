package com.trackzio.iris.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/search")
    suspend fun searchCities(
        @Query("name") city: String,
        @Query("count") count: Int = 5
    ): CityResponse

    @GET("https://api.open-meteo.com/v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current")
        current: String =
            "temperature_2m,relative_humidity_2m,wind_speed_10m,pressure_msl"
    ): WeatherResponse
}