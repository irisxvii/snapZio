package com.trackzio.iris.data.remote

data class CityResponse(
    val results: List<City>?
)

data class City(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)