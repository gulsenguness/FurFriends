package com.gulsenurgunes.furfriends.ui.auth.model

data class SocialProvider(
    val name: String,
    val iconRes: Int,
    val onClick: () -> Unit
)