<h1>JPMCWeatherApp Coding Challenge Description</h1>

Create an Android native-app-based application to serve as a basic weather app.
Search Screen
Allow customers to enter a US city
Call the openweathermap.org API and display the information you think a user would be interested in seeing. Be sure to has the app download and display a weather icon.
Have image cache if needed
Auto-load the last city searched upon app launch.
Ask the User for location access, If the User gives permission to access the location, then retrieve weather data by default

<h2>What I Did</h2>
This project is developed in an MVVM architecture using coroutine and GSON packages to handle this data.
I imported the sample JSON and created objects using the GSON package. Retrieving data via retrofit from the openweathermap API.
For the UI, I have used Jetpack Compose and Navigation. For dependency injection, I decided to use Hilt.
