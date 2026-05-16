package com.trackzio.iris.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

import androidx.compose.foundation.clickable
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

import com.trackzio.iris.data.remote.CurrentWeather
import com.trackzio.iris.ui.camera.CameraScreen
import com.trackzio.iris.ui.report.ReportScreen
import com.trackzio.iris.utils.compressImage

import androidx.compose.ui.platform.LocalContext
import com.trackzio.iris.data.local.WeatherDatabase

import com.trackzio.iris.data.local.WeatherReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WScreen() {
    val viewModel: WeatherViewModel = viewModel()

    val cities by viewModel.cities.collectAsState()

    var city by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val weather by viewModel.weather.collectAsState()

    var selectedCity by remember {
        mutableStateOf<com.trackzio.iris.data.remote.City?>(null)
    }

    var searchedCityName by remember {
        mutableStateOf("")
    }

    var showReportScreen by remember {
        mutableStateOf(false)
    }

    var showCameraScreen by remember {
        mutableStateOf(false)
    }

    var capturedImagePath by remember {
        mutableStateOf<String?>(null)
    }

    var compressedImagePath by remember {
        mutableStateOf<String?>(null)
    }

    var originalImageSize by remember {
        mutableStateOf(0)
    }

    var compressedImageSize by remember {
        mutableStateOf(0)
    }

    var notes by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val database = remember {
        WeatherDatabase.getDatabase(context)
    }

    var showSavedReportsScreen by remember {
        mutableStateOf(false)
    }

    if (showCameraScreen) {
        CameraScreen(
            onClose = {
                showCameraScreen = false
            },
            onCapture = { imagePath ->
                val result =
                    compressImage(imagePath)

                capturedImagePath =
                    result.compressedFile.absolutePath

                compressedImagePath =
                    result.compressedFile.absolutePath

                originalImageSize =
                    result.originalSizeKb

                compressedImageSize =
                    result.compressedSizeKb

                showCameraScreen = false
            }
        )

    }

    else if (showSavedReportsScreen) {

    }

    else if (showReportScreen) {
        ReportScreen(
            cityName = searchedCityName,
            weather = weather!!,
            onBackClick = {
                showReportScreen = false
            },
            onCapturePhotoClick = {
                showCameraScreen = true
            },
            imagePath = capturedImagePath,
            originalImageSize = originalImageSize,
            compressedImageSize = compressedImageSize,
            notes = notes,
            onNotesChange = {
                notes = it
            },
            onSaveReportClick = {
                if (capturedImagePath != null) {
                    val report =
                        WeatherReport(
                            cityName = searchedCityName,
                            temperature =
                                weather!!.temperature_2m,
                            humidity =
                                weather!!.relative_humidity_2m,
                            windSpeed =
                                weather!!.wind_speed_10m,
                            pressure =
                                weather!!.pressure_msl,
                            imagePath =
                                capturedImagePath!!,
                            originalImageSize =
                                originalImageSize,
                            compressedImageSize =
                                compressedImageSize,
                            notes = notes,
                            timestamp =
                                System.currentTimeMillis()
                        )

                    CoroutineScope(Dispatchers.IO)
                        .launch {

                            database
                                .reportDao()
                                .insertReport(report)
                        }

                    showReportScreen = false
                    showSavedReportsScreen = true
                }
            }
        )

    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D3200))
                .padding(
                    top = 39.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {

            HeaderCard(
                title = "WeatherSnap",
                subtitle = "Live weather reports with camera evidence",
                buttonText = "Reports",
                onButtonClick = { }
            )

            Spacer(modifier = Modifier.height(20.dp))

            SearchSection(
                city = city,
                cities = cities,
                onCityChange = {
                    city = it
                },
                onSearchQueryChange = {
                    viewModel.searchCities(it)
                },
                onClearCities = {
                    viewModel.clearCities()
                },
                onSearchClick = {
                    selectedCity?.let {
                        searchedCityName = it.name
                        viewModel.fetchWeather(it)
                    }
                },
                onCitySelected = {
                    selectedCity = it
                }
            )
            if (weather == null) {
                EmptyWeatherCard()
            } else {
                WeatherCard(
                    cityName = searchedCityName,
                    weather = weather!!,
                    onCreateReportClick = {
                        showReportScreen = true
                    }
                )
            }
        }
    }
}

@Composable
fun HeaderCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB8C9A3)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2D3200)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onButtonClick,
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4B4F00),
                    contentColor = Color(0xFFD8E08B)
                )
            ) {
                Text(
                    text = buttonText,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SearchSection(
    city: TextFieldValue,
    cities: List<com.trackzio.iris.data.remote.City>,
    onCityChange: (TextFieldValue) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClearCities: () -> Unit,
    onSearchClick: () -> Unit,
    onCitySelected: (com.trackzio.iris.data.remote.City) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111400)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        onCityChange(it)
                        onSearchQueryChange(it.text)
                    },
                    label = {
                        Text("City")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                )

                Button(
                    onClick = onSearchClick,
                    modifier = Modifier.height(46.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD8E08B)
                    )
                ) {
                    Text(
                        text = "Search",
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Enter more than 2 letters to start city suggestions.",
                color = Color.LightGray,
                fontSize = 12.sp
            )
            cities.forEach { item ->
                Text(
                    text = "${item.name}, ${item.country}",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onCitySelected(item)
                            onCityChange(
                                TextFieldValue(
                                    text = item.name,
                                    selection = TextRange(item.name.length)
                                )
                            )
                            onClearCities()
                        }
                )
            }
        }
    }
}

@Composable
fun EmptyWeatherCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3A3A3A)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                            listOf(
                                Color(0xFF5A6B4D),
                                Color(0xFF1F7A7A)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Search. Capture. Save.",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "No weather loaded",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Enter more than 2 letters, choose a city, then search.",
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun WeatherCard(
    cityName: String,
    weather: CurrentWeather,
    onCreateReportClick: () -> Unit,
    showReportActions: Boolean = true
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3A3A3A)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column {
                    Text(
                        text = cityName,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Partly cloudy",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF9CAB1A)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "${weather.temperature_2m.toInt()}°C",
                        modifier = Modifier.padding(
                            horizontal = 18.dp,
                            vertical = 14.dp
                        ),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                WeatherInfoCard(
                    title = "Humidity",
                    value = "${weather.relative_humidity_2m}%",
                    accent = Color(0xFF00C853),
                    modifier = Modifier.weight(1f)
                )

                WeatherInfoCard(
                    title = "Wind",
                    value = "${weather.wind_speed_10m} m/s",
                    accent = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )

                WeatherInfoCard(
                    title = "Pressure",
                    value = "${weather.pressure_msl}",
                    accent = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
            }

            if (showReportActions) {
                Spacer(modifier = Modifier.height(18.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4A4A4A)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Report readiness",
                            color = Color.LightGray,
                            fontSize = 13.sp,
                        )

                        Text(
                            text = "Camera and Room DB enabled",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onCreateReportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD8E08B)
                    ),
                    shape = RoundedCornerShape(50)
                ) {

                    Text(
                        text = "Create Report",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherInfoCard(
    title: String,
    value: String,
    accent: Color,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF444444)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = title,
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Text(
                text = value,
                color = accent,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}