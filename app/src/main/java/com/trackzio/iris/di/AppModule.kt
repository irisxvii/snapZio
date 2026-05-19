package com.trackzio.iris.di

import android.content.Context
import androidx.room.Room
import com.trackzio.iris.data.local.WeatherDatabase
import com.trackzio.iris.data.remote.RetrofitInstance
import com.trackzio.iris.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }
}