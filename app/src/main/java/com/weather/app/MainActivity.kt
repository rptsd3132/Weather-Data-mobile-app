package com.weather.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.FlowRow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherScreen()
        }
    }
}

@Composable
fun WeatherScreen(vm: WeatherViewModel = viewModel()) {
    // Load cities once when the screen first appears
    LaunchedEffect(Unit) { vm.loadCities() }

    val cities = vm.cities.value
    val selectedCity = vm.selectedCity.value
    val weather = vm.weather.value
    val loading = vm.loading.value
    val error = vm.error.value

    // Purple gradient background
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))

            // Header
            Text(
                text = "🌤️ Weather Dashboard",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Live Weather Data",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(24.dp))


            // City buttons
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cities.forEach { city ->
                    val isSelected = city == selectedCity
                    Button(
                        onClick = { vm.selectCity(city) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color.White
                            else Color.White.copy(alpha = 0.2f)
                        )
                    ) {
                        Text(
                            text = city,
                            color = if (isSelected) Color(0xFF764BA2) else Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Loading / error / weather
            when {
                loading -> {
                    CircularProgressIndicator(color = Color.White)
                }
                error != null -> {
                    Text(text = error, color = Color.White)
                }
                weather != null -> {
                    WeatherCard("Temperature", "${weather.temperature}°C")
                    Spacer(Modifier.height(12.dp))
                    WeatherCard("Feels Like", "${weather.feels_like}°C")
                    Spacer(Modifier.height(12.dp))
                    WeatherCard("Humidity", "${weather.humidity}%")
                    Spacer(Modifier.height(12.dp))
                    WeatherCard("Wind Speed", "${weather.wind_speed}")
                    Spacer(Modifier.height(12.dp))
                    WeatherCard("Condition", weather.description)
                }
            }
        }
    }
}

@Composable
fun WeatherCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label.uppercase(),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}