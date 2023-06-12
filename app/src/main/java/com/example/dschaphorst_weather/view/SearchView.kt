package com.example.dschaphorst_weather.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dschaphorst_weather.viewmodel.WeatherViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {
    val shouldGetLocation = remember { mutableStateOf(false) }
    val citySearch = remember { mutableStateOf(TextFieldValue(weatherViewModel.selectedCity ?: "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = citySearch.value,
            onValueChange = { txt ->
                weatherViewModel.isLocation.value?.let {
                    if (it) {
                        weatherViewModel.selectedCity?.let {city ->
                            citySearch.value = TextFieldValue(city)
                        } ?: let {
                            weatherViewModel.selectedCity = citySearch.value.text
                            citySearch.value = txt
                        }
                    } else {
                        weatherViewModel.selectedCity = citySearch.value.text
                        citySearch.value = txt
                    }
                } ?: let {
                    weatherViewModel.selectedCity = citySearch.value.text
                    citySearch.value = txt
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (citySearch.value.text.isNotEmpty()) {
                        weatherViewModel.selectedCity = citySearch.value.text
                        weatherViewModel.getWeather()
                        navController?.navigate("weather")
                    } else {

                    }

                }) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null
                    )
                }
            },
            placeholder = { Text(text = "Enter City Name") },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
            ),
            maxLines = 1,
            singleLine = true,
        )
        Button(
            onClick = {
                shouldGetLocation.value = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "WeatherByLocation")
        }
    }

    if (shouldGetLocation.value) {
        AskPermissions(weatherViewModel)
        shouldGetLocation.value = false
    }
}