package com.trackzio.iris.ui.report.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File

@Composable
fun PhotoPreviewCard(
    imagePath: String?,
    onCapturePhotoClick: () -> Unit,
    originalImageSize: Int,
    compressedImageSize: Int,
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

            if (imagePath != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Original: ${originalImageSize} KB",
                    color = Color.LightGray
                )
                Text(
                    text = "Compressed: ${compressedImageSize} KB",
                    color = Color.LightGray
                )
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
                    text = if (imagePath != null) "Retake Photo" else "Capture Photo",
                    color = Color.Black
                )
            }
        }
    }
}
