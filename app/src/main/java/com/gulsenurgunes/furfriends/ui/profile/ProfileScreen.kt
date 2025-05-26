package com.gulsenurgunes.furfriends.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.ui.home.HomeTopBar


@Composable
fun ProfileScreen() {
    val snackHost = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        topBar = { HomeTopBar { } }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(44.dp))
            QuickActionRowPreview()
            Spacer(Modifier.height(24.dp))
            Section("Account Setting")
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Edit Profile",
            )
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Saved Cards & Wallet",
            )
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Saved Addresses",
            )
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Select Language",
            )
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Notifications Settings",
            )
            Spacer(Modifier.height(24.dp))
            Section("MyActivity")
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Reviews",
            )
            SectionInside(
                icon = Icons.Default.ShoppingCart,
                text = "Questions & Answers",
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilPreview() {
    ProfileScreen()
}