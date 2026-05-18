package com.trackzio.iris.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.trackzio.iris.ui.weather.components.HeaderCard
import com.trackzio.iris.ui.weather.components.WeatherCard
import com.trackzio.iris.data.remote.CurrentWeather

import coil.compose.AsyncImage
import java.io.File

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.trackzio.iris.ui.report.components.*

@Composable
fun ReportScreen(
    cityName: String,
    weather: CurrentWeather,
    onBackClick: () -> Unit,
    onCapturePhotoClick: () -> Unit,
    imagePath: String?,
    originalImageSize: Int,
    compressedImageSize: Int,
    notes: String,
    onNotesChange: (String) -> Unit,
    onSaveReportClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3200))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        HeaderCard(
            title = "Create Report",
            subtitle = "Capture, compress, annotate",
            buttonText = "Back",
            onButtonClick = onBackClick
        )

        WeatherCard(
            cityName = cityName,
            weather = weather,
            onCreateReportClick = { },
            showReportActions = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        PhotoPreviewCard(
            imagePath = imagePath,
            onCapturePhotoClick = onCapturePhotoClick,
            originalImageSize = originalImageSize,
            compressedImageSize = compressedImageSize
        )

        Spacer(modifier = Modifier.height(16.dp))

        NotesSection(
            notes = notes,
            onNotesChange = onNotesChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSaveReportClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD8E08B)
            ),
            shape = RoundedCornerShape(50)
        ) {

            Text(
                text = "Save Report",
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}