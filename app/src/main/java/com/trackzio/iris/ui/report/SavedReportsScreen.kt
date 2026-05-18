package com.trackzio.iris.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trackzio.iris.data.local.WeatherReport
import com.trackzio.iris.ui.weather.components.HeaderCard
import com.trackzio.iris.ui.report.components.*

@Composable
fun SavedReportsScreen(
    reports: List<WeatherReport>,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3200))
            .padding(
                top = 39.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {

        HeaderCard(
            title = "Saved Reports",
            subtitle = " ",
            buttonText = "Back",
            onButtonClick = onBackClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (reports.isEmpty()) {
            EmptyReportsState()
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
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