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

@Composable
fun WScreen() {
    val viewModel: WeatherViewModel = viewModel()

    val cities by viewModel.cities.collectAsState()

    var city by remember {
        mutableStateOf("")
    }

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

        HeaderCard()

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
            }
        )
    }
}

@Composable
fun HeaderCard() {

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
                    text = "WeatherSnap",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2D3200)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Live weather reports with camera evidence",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = { },
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
                    text = "Reports",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SearchSection(
    city: String,
    cities: List<com.trackzio.iris.data.remote.City>,
    onCityChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClearCities: () -> Unit
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
                        onSearchQueryChange(it)
                    },
                    label = {
                        Text("City")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                )

                Button(
                    onClick = { },
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
                            onCityChange(item.name)
                            onClearCities()
                        }
                )
            }
        }
    }
}