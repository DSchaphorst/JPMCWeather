package com.example.dschaphorst_weather.network

import com.example.dschaphorst_weather.model.WeatherResponse
import com.example.dschaphorst_weather.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface WeatherRepository {

    fun getWeather(
        city: String,
        state: String? = null,
        country: String? = null
    ): Flow<Resource<WeatherResponse>>

    suspend fun getCityName(
        lat: Double,
        lon: Double
    ): String
}

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAPI: WeatherAPI,
    private val ioDispatcher: CoroutineDispatcher
): WeatherRepository {

    override fun getWeather(
        city: String,
        state: String?,
        country: String?
    ): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.LOADING)

        try {
            val response = weatherAPI.getWeatherByCity(city)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.SUCCESS(it))
                } ?: throw Exception("Network Error")
            } else {
                throw Exception(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            emit(Resource.ERROR(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getCityName(lat: Double, lon: Double): String {
        return weatherAPI.getGeocodeName(lat, lon).body()?.firstOrNull()?.name ?: ""
    }

}