package com.example.dschaphorst_weather.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dschaphorst_weather.model.WeatherResponse
import com.example.dschaphorst_weather.network.WeatherRepository
import com.example.dschaphorst_weather.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val sharedPreferences: SharedPreferences,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    private val _weather: MutableState<Resource<WeatherResponse>> = mutableStateOf(Resource.LOADING)
    private val _isCitySelected: MutableState<Boolean?> = mutableStateOf(null)

    val weather: State<Resource<WeatherResponse>> get() = _weather
    val isCitySelected: State<Boolean?> get() = _isCitySelected

    var selectedCity: String?
        get() = sharedPreferences.getString(CITY_KEY, null)
        set(value) {
            sharedPreferences.edit {
                putString(CITY_KEY, value)
                apply()
            }
        }

    private val _isLocation: MutableState<Boolean?> = mutableStateOf(null)
    val isLocation: State<Boolean?> get() = _isLocation

    fun getWeather() {
        viewModelScope.launch(ioDispatcher) {
            selectedCity?.let {
                weatherRepository.getWeather(it).collect() {
                    _weather.value = it
                }
            } ?: let {
                _weather.value = Resource.ERROR(Exception("City not selected"))
            }
        }
    }

    private fun getCityName(location: Location) {
        viewModelScope.launch {
            selectedCity = weatherRepository.getCityName(location.latitude, location.longitude)
            _isCitySelected.value = true
        }
    }

    @SuppressLint("MissingPermission") // NOTE: Shortcut for convenience of sample project.
    fun getLocation() {
        val locationTask = fusedLocationProviderClient.lastLocation

        locationTask.addOnSuccessListener {
            if (it != null) {
                getCityName(it)
            }
        }
        locationTask.addOnFailureListener { _isCitySelected.value = false }
    }

    companion object {
        private const val CITY_KEY = "CITY_KEY"
    }
}