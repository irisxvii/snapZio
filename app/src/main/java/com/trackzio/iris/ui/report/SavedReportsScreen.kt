package com.trackzio.iris.ui.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight
import com.trackzio.iris.data.local.WeatherReport
import com.trackzio.iris.ui.weather.HeaderCard

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SavedReportsScreen(
    reports: List<WeatherReport>,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3200))
    ) {

        HeaderCard(
            title = "Saved Reports",
            subtitle = " ",
            buttonText = "Back",
            onButtonClick = onBackClick
        )

        if (reports.isEmpty()) {
            EmptyReportsState()
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {

                items(reports) { report ->

                    SavedReportCard(report)
                }
            }
        }
    }
}

@Composable
fun EmptyReportsState() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3A3A3A)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush =
                            androidx.compose.ui.graphics.Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF556B2F),
                                    Color(0xFF1F7A7A)
                                )
                            ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "No reports yet",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text =
                    "Create and save a weather report to see image, notes, and weather details here.",
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun SavedReportCard(
    report: WeatherReport
) {

    val formattedDate =
        SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date(report.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3A3A3A)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            AsyncImage(
                model = File(report.imagePath),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column {
                    Text(
                        text = report.cityName,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Partly cloudy",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = formattedDate,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF8C9B00)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text =
                            "${report.temperature.toInt()}°C",

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

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                SizeInfoCard(
                    title = "Original",
                    value =
                        "${report.originalImageSize} KB",

                    accent = Color(0xFFFF9800),

                    modifier = Modifier.weight(1f)
                )

                SizeInfoCard(
                    title = "Compressed",
                    value =
                        "${report.compressedImageSize} KB",

                    accent = Color(0xFF00BCD4),

                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = report.notes,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SizeInfoCard(
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
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                color = accent,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}