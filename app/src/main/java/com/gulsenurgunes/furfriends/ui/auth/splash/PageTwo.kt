package com.gulsenurgunes.furfriends.ui.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.auth.components.ScreenHeader


@Composable
fun PageTwo(
    onGetStarted: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScreenHeader(
            "Adopt Your The Best Friends",
            "Buy your BFF small gifts and adopt one if you don't have one.\",\"We have the tools, training and experience to diagnose life-threatening illnesses and injuries.",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.animal2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
        )
        Spacer(modifier = Modifier.height(124.dp))
        Circle()
        Spacer(modifier = Modifier.height(16.dp))
        AuthButton(
            text = "Get Started",
            onClick = onGetStarted,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PageTwoPreview() {
    PageTwo(onGetStarted = {})
}