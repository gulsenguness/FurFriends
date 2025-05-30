package com.gulsenurgunes.furfriends.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun SelectableItemRow(title: String, items: List<String>) {
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Text(title,fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Row {
        items.forEach { item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (selectedItem == item) Color.Black else Color.White,
                        CircleShape
                    )
                    .border(1.dp, Color.Black, CircleShape)
                    .clickable { selectedItem = item }
            ) {
                Text(
                    text = item,
                    color = if (selectedItem == item) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}