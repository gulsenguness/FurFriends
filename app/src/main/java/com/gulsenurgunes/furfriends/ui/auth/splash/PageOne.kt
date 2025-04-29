package com.gulsenurgunes.furfriends.ui.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulsenurgunes.furfriends.R
import kotlinx.coroutines.delay

@Composable
fun PageOne(
    modifier: Modifier = Modifier,
    overlayColor: Color = Color(0xFFB8171E).copy(alpha = 0.75f),
    versionText: String = "VERSION 1.0",
    onTimeout: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(2000L)
        onTimeout()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.animal3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(overlayColor)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = "PetPerks Logo",
            modifier = Modifier
                .size(120.dp)                                     // 120x120 dp kare
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "PetPerks",
            fontSize = 36.sp,
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = versionText,
            style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PageOnePreview() {
    PageOne()
}