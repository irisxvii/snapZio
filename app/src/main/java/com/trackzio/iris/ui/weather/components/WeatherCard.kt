package com.trackzio.iris.ui.weather.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trackzio.iris.data.remote.CurrentWeather
import com.trackzio.iris.utils.getWeatherDescription

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
                        text = getWeatherDescription(weather.weather_code),
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
