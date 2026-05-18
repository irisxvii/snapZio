package com.trackzio.iris.ui.weather.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
