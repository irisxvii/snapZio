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

import com.trackzio.iris.ui.weather.HeaderCard
import com.trackzio.iris.ui.weather.WeatherCard
import com.trackzio.iris.data.remote.CurrentWeather

import coil.compose.AsyncImage
import java.io.File

@Composable
fun ReportScreen(
    cityName: String,
    weather: CurrentWeather,
    onBackClick: () -> Unit,
    onCapturePhotoClick: () -> Unit,
    imagePath: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3200))
            .padding(16.dp)
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
            onCapturePhotoClick = onCapturePhotoClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        NotesSection()

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { },
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

@Composable
fun PhotoPreviewCard(
    imagePath: String?,
    onCapturePhotoClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    .height(180.dp)
                    .background(
                        Color(0xFF556B2F),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                if (imagePath != null) {
                    AsyncImage(
                        model = File(imagePath),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {

                    Text(
                        text = "Photo preview",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCapturePhotoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD8E08B)
                ),
                shape = RoundedCornerShape(50)
            ) {

                Text(
                    text =
                        if (imagePath != null)
                            "Retake Photo"
                        else
                            "Capture Photo",

                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun NotesSection() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3A3A3A)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Field Notes",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = {
                    Text("Notes")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
        }
    }
}