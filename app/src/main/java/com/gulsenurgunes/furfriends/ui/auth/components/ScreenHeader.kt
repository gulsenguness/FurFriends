package com.gulsenurgunes.furfriends.ui.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    titleFontSize: TextUnit = 28.sp,
    subtitleFontSize: TextUnit = 16.sp
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Text(
            text = title,
            fontSize = titleFontSize,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = subtitle,
            fontSize = subtitleFontSize,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}