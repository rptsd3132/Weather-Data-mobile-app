package com.weather.app

// Describes one weather record from the API
data class Weather(
    val id: Int,
    val city: String,
    val temperature: Double,
    val feels_like: Double,
    val humidity: Int,
    val description: String,
    val wind_speed: Double,
    val recorded_at: String
)