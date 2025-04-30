package com.gulsenurgunes.furfriends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gulsenurgunes.furfriends.navigation.AppNavHost
import com.gulsenurgunes.furfriends.ui.theme.FurFriendsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FurFriendsTheme {
                AppNavHost()
            }
        }
    }
}