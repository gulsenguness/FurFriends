package com.gulsenurgunes.furfriends.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorSelectionRow() {
    var selectedColor by remember { mutableStateOf<Color?>(null) }
    Text("Items Color :", fontSize = 18.sp, style = MaterialTheme.typography.titleMedium,fontWeight = FontWeight.Bold)
    Row {
        val colors = listOf(
            Color(0xFFB35650),
            Color(0xFF4A6AD3),
            Color(0xFFC59A66),
            Color(0xFFA0A0A0),
            Color(0xFFA366CC),
            Color(0xFF6AA84F)
        )
        colors.forEach { color ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = if (selectedColor == color) 2.dp else 0.dp,
                        color = if (selectedColor == color) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(5.dp)
                    .background(color, CircleShape)
                    .clickable { selectedColor = color }
            ) {
                if (selectedColor == color) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}