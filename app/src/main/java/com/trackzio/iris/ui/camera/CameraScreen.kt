package com.trackzio.iris.ui.camera

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

@Composable
fun CameraScreen(
    onClose: () -> Unit,
    onCapture: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Custom Camera",
                color = Color.White,
                fontSize = 20.sp
            )

            OutlinedButton(
                onClick = onClose,
                shape = RoundedCornerShape(50)
            ) {

                Text(
                    text = "Close"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onCapture,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD8E08B)
            ),
            shape = RoundedCornerShape(50)
        ) {

            Text(
                text = "Capture",
                color = Color.Black
            )
        }
    }
}