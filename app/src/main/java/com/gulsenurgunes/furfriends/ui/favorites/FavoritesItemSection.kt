package com.gulsenurgunes.furfriends.ui.favorites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesItemSection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val items =
        listOf("All", "Clothes", "Pillow", "Toy", "Bowl", "Belt", "Bag", "Care", "Nutrients")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(items) { item ->
            val isSelected = item == selectedCategory
            OutlinedButton(
                onClick = {
                    onCategorySelected(item)
                },
                modifier = Modifier
                    .height(40.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected) Color.Black else Color.White,
                    contentColor = if (isSelected) Color.White else Color.Black
                )
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

