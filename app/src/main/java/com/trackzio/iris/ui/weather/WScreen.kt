package com.trackzio.iris.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.text.input.TextFieldValue

import com.trackzio.iris.ui.camera.CameraScreen
import com.trackzio.iris.ui.report.ReportScreen
import com.trackzio.iris.ui.report.SavedReportsScreen
import com.trackzio.iris.ui.weather.components.*
import com.trackzio.iris.utils.compressImage

import com.trackzio.iris.data.local.WeatherReport

@Composable
fun WScreen() {
    val viewModel: WeatherViewModel = hiltViewModel()

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

    var showSavedReportsScreen by remember {
        mutableStateOf(false)
    }

    val savedReports by viewModel.savedReports.collectAsState()

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
        SavedReportsScreen(
            reports = savedReports,
            onBackClick = {
                showSavedReportsScreen = false
            }
        )
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
                                System.currentTimeMillis(),
                            weatherCode = weather!!.weather_code
                        )

                    viewModel.saveReport(report)

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
                onButtonClick = {
                    viewModel.loadReports()
                    showSavedReportsScreen = true
                }
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