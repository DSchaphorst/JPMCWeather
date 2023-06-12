package com.example.dschaphorst_weather.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.dschaphorst_weather.viewmodel.WeatherViewModel

val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

@Composable
fun AskPermissions(
    weatherViewModel: WeatherViewModel
) {
    // code to check permissions
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permsMap ->
        val areGranted = permsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            weatherViewModel.getLocation()
        } else {

        }
    }

    if(
        permissions.all {
            ContextCompat.checkSelfPermission(
                LocalContext.current,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        weatherViewModel.getLocation()
    } else {
        SideEffect {
            launcher.launch(permissions)
        }
    }
}