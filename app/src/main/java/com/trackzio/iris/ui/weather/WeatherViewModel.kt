package com.trackzio.iris.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackzio.iris.data.remote.City
import com.trackzio.iris.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _cities =
        MutableStateFlow<List<City>>(emptyList())

    val cities: StateFlow<List<City>> = _cities

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
}