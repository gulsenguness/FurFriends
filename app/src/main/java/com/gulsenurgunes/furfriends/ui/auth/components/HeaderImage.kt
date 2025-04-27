package com.gulsenurgunes.furfriends.ui.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HeaderImage(
    imageRes: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    heightFraction: Float = 0.25f,
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxHeight(heightFraction)
            .clip(RoundedCornerShape(bottomEnd = 126.dp))
    )
}