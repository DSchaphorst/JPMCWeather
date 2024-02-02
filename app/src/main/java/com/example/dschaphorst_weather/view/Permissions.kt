package com.example.dschaphorst_weather.view

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.dschaphorst_weather.viewmodel.WeatherViewModel

val permissions = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)

@Composable
fun AskPermissions(
    weatherViewModel: WeatherViewModel
) {
    // code to check permissions
    if (permissions.any {
            ActivityCompat.checkSelfPermission(
                LocalContext.current,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        weatherViewModel.getLocation()
    }
}