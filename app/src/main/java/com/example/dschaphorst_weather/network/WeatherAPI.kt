package com.example.dschaphorst_weather.network

import com.example.dschaphorst_weather.model.WeatherResponse
import com.example.dschaphorst_weather.model.geocoding.GeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET(PATH_CURRENT_WEATHER)
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appid: String = API_KEY
    ): Response<WeatherResponse>

    @GET(PATH_GEOCODER)
    suspend fun getGeocodeName(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 1,
        @Query("appid") appid: String = API_KEY
    ): Response<GeocodingResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val BASE_IMAGE = "https://openweathermap.org/img/wn/"
        private const val PATH_CURRENT_WEATHER ="data/2.5/weather"
        private const val PATH_GEOCODER = "geo/1.0/reverse"
        // NOTE: Api key should be protected, such as in BuildConfig or Keystore. Leaving hard coded for convenience of sample project.
        const val API_KEY = "7c7de886286fe17737d8ffeb335087f5"
    }
}