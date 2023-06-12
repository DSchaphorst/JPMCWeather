package com.example.dschaphorst_weather.utils

sealed class Resource<out T> {
    object LOADING : Resource<Nothing>()
    data class SUCCESS<T>(val data: T) : Resource<T>()
    data class ERROR(val error: Exception) : Resource<Nothing>()
}
