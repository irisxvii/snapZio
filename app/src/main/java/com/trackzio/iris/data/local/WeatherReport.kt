package com.trackzio.iris.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_reports")
data class WeatherReport(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Double,
    val imagePath: String,
    val originalImageSize: Int,
    val compressedImageSize: Int,
    val notes: String,
    val timestamp: Long,
    val weatherCode: Int = 0
)