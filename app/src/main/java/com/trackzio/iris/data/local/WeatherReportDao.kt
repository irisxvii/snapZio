package com.trackzio.iris.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherReportDao {

    @Insert
    suspend fun insertReport(
        report: WeatherReport
    )

    @Query(
        "SELECT * FROM weather_reports ORDER BY timestamp DESC"
    )
    suspend fun getAllReports():
            List<WeatherReport>
}