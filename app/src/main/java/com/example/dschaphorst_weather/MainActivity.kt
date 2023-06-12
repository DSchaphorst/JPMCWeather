package com.example.dschaphorst_weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dschaphorst_weather.ui.theme.DSchaphorst_WeatherTheme
import com.example.dschaphorst_weather.view.SearchScreen
import com.example.dschaphorst_weather.view.WeatherDetails
import com.example.dschaphorst_weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSchaphorst_WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<WeatherViewModel>()

                    NavHost(navController = navController, startDestination = "search") {
                        composable(route = "search") {
                            SearchScreen(weatherViewModel = viewModel, navController = navController)
                        }

                        composable(route = "weather") {
                            WeatherDetails(weatherViewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DSchaphorst_WeatherTheme {
        Greeting("Android")
    }
}