package com.trackzio.iris.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackzio.iris.data.remote.City
import com.trackzio.iris.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.trackzio.iris.data.remote.CurrentWeather

class WeatherViewModel : ViewModel() {

    private val _cities =
        MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    private val _weather =
        MutableStateFlow<CurrentWeather?>(null)
    val weather: StateFlow<CurrentWeather?> =
        _weather

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
                val response =
                    RetrofitInstance.api.searchCities(query)

                _cities.value =
                    response.results ?: emptyList()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeather(city: City) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.api.getWeather(
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