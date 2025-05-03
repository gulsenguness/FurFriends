package com.gulsenurgunes.furfriends.ui.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.ui.auth.model.SocialProvider

@Composable
fun SocialIconsRow(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit = {},
    onAppleClick: () -> Unit = {}
) {
    val providers = listOf(
        SocialProvider("Google", R.drawable.google, onGoogleClick),
        SocialProvider("Facebook", R.drawable.facebook, onFacebookClick),
        SocialProvider("Apple", R.drawable.apple, onAppleClick)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        providers.forEach { provider ->
            IconButton(
                onClick = provider.onClick,
                modifier = Modifier
                    .size(56.dp)
                    .padding(horizontal = 8.dp)
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(id = provider.iconRes),
                    contentDescription = "${provider.name} ile giriş",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}