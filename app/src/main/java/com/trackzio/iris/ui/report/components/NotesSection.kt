package com.trackzio.iris.ui.report.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotesSection(
    notes: String,
    onNotesChange: (String) -> Unit
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
            Text(
                text = "Field Notes",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = onNotesChange,
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
