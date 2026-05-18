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
