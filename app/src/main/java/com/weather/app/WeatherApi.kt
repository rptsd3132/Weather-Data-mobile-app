package com.weather.app

import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    // GET /cities  -> returns a list of city names
    @GET("cities")
    suspend fun getCities(): List<String>

    // GET /weather/{city}  -> returns the latest weather for a city
    @GET("weather/{city}")
    suspend fun getWeather(@Path("city") city: String): Weather

    // GET /weather/{city}/history  -> returns all past records for a city
    @GET("weather/{city}/history")
    suspend fun getHistory(@Path("city") city: String): List<Weather>
}

