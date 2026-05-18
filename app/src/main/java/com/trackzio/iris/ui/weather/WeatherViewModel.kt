package com.trackzio.iris.ui.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trackzio.iris.data.local.WeatherDatabase
import com.trackzio.iris.data.local.WeatherReport
import com.trackzio.iris.data.remote.City
import com.trackzio.iris.data.remote.CurrentWeather
import com.trackzio.iris.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val database = WeatherDatabase.getDatabase(application)

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    private val _weather = MutableStateFlow<CurrentWeather?>(null)
    val weather: StateFlow<CurrentWeather?> = _weather

    private val _savedReports = MutableStateFlow<List<WeatherReport>>(emptyList())
    val savedReports: StateFlow<List<WeatherReport>> = _savedReports

    fun loadReports() {
        viewModelScope.launch(Dispatchers.IO) {
            val reports = database.reportDao().getAllReports()
            _savedReports.value = reports
        }
    }

    fun saveReport(report: WeatherReport) {
        viewModelScope.launch(Dispatchers.IO) {
            database.reportDao().insertReport(report)
            loadReports()
        }
    }

    fun clearCities() {
        _cities.value = emptyList()
    }

    fun searchCities(query: String) {
        if (query.length < 2) {
            _cities.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchCities(query)
                _cities.value = response.results ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeather(city: City) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(
                    latitude = city.latitude,
                    longitude = city.longitude
                )
                _weather.value = response.current
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}