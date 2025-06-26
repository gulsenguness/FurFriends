package com.gulsenurgunes.furfriends.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.navigation.Screen
import com.gulsenurgunes.furfriends.navigation.TopBar

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val snackHost = remember { SnackbarHostState() }
    val displayName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Kullanıcı"

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        topBar = {
            ProfileTopBar(
                onSearchClick = {},
                onNotificationsClick = {},
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(44.dp))
            ProfileHeader(displayName = displayName)
            Spacer(Modifier.height(24.dp))
            QuickActionRowPreview()
            Spacer(Modifier.height(24.dp))
            Section("Account Setting")
            SectionInside(
                icon = R.drawable.person,
                text = "Edit Profile",
                onClick ={
                    navController.navigate(Screen.EditProfile.route)
                }
            )
            SectionInside(
                icon = R.drawable.card,
                text = "Saved Cards & Wallet",
                onClick ={
                    navController.navigate(Screen.SavedCards.route)
                }
            )
            SectionInside(
                icon = R.drawable.location,
                text = "Saved Addresses",
                onClick ={
                    navController.navigate(Screen.SavedAddresses.route)
                }
            )
            SectionInside(
                icon = R.drawable.language,
                text = "Select Language",
                onClick ={}
            )
            SectionInside(
                icon = R.drawable.notifications,
                text = "Notifications Settings",
                onClick ={}
            )
            Spacer(Modifier.height(24.dp))
            Section("MyActivity")
            SectionInside(
                icon = R.drawable.reviews,
                text = "Reviews",
                onClick ={}
            )
            SectionInside(
                icon = R.drawable.question,
                text = "Questions & Answers",
                onClick ={}
            )
        }
    }
}

@Composable
fun ProfileHeader(displayName: String, profileImageRes: Int = R.drawable.first) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = profileImageRes),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Hello, $displayName",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ProfileTopBar(
    onNotificationsClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) = TopBar(
    title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.paw),
                contentDescription = "Logo",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "FurFriends",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    },
    actions = {
        IconButton(onClick = onNotificationsClick) {
            Icon(Icons.Default.Notifications, contentDescription = null)
        }
        IconButton(onClick = onSearchClick) {
            Icon(Icons.Default.Search, contentDescription = null)
        }
    }
)
