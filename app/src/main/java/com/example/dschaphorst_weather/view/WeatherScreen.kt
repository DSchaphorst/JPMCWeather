package com.example.dschaphorst_weather.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dschaphorst_weather.R
import com.example.dschaphorst_weather.model.WeatherResponse
import com.example.dschaphorst_weather.network.WeatherAPI
import com.example.dschaphorst_weather.utils.Resource
import com.example.dschaphorst_weather.viewmodel.WeatherViewModel

@Composable
fun WeatherDetails(
    weatherViewModel: WeatherViewModel
) {
    when (val state = weatherViewModel.weather.value) {
        is Resource.ERROR -> {}
        is Resource.LOADING -> {}
        is Resource.SUCCESS -> {
            DetailsList(state.data)
        }
    }
}

@Composable
fun DetailsList(
    weathers: WeatherResponse? = null
) {
    LazyColumn {
        itemsIndexed(items = listOf(weathers)) { _, sat ->
            sat?.let {
                DetailItem(weather = it)
            } ?: Text(text = "No information available for specific school")
        }
    }
}

@Composable
fun DetailItem(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Temperature: ${weather.main?.temp.toString()}")
        Text(text = "Max Temperature: ${weather.main?.tempMax.toString()}")
        Text(text = "Min Temperature: ${weather.main?.tempMin.toString()}")
        Text(text = "Temperature Feels Like: ${weather.main?.feelsLike.toString()}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    WeatherAPI.BASE_IMAGE +
                            weather.weather?.get(0)?.icon +
                            "@2x.png"
                )
                .crossfade(true)
                .build(), placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = weather.weather?.get(0)?.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )
    }
}