package com.weather.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    // State the UI will observe
    private val _cities = mutableStateOf<List<String>>(emptyList())
    val cities: State<List<String>> = _cities

    private val _selectedCity = mutableStateOf<String?>(null)
    val selectedCity: State<String?> = _selectedCity

    private val _weather = mutableStateOf<Weather?>(null)
    val weather: State<Weather?> = _weather

    private val _history = mutableStateOf<List<Weather>>(emptyList())
    val history: State<List<Weather>> = _history

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    // Load the list of cities when the app starts
    fun loadCities() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getCities()
                _cities.value = result
                if (result.isNotEmpty()) {
                    selectCity(result[0])
                }
            } catch (e: Exception) {
                _error.value = "Failed to load cities: ${e.message}"
            }
        }
    }

    // Load weather + history for a chosen city
    fun selectCity(city: String) {
        _selectedCity.value = city
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _weather.value = RetrofitClient.api.getWeather(city)
                _history.value = RetrofitClient.api.getHistory(city)
            } catch (e: Exception) {
                _error.value = "Failed to load weather: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}