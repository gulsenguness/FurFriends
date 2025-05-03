package com.gulsenurgunes.furfriends.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomTab(val route: String, val label: String, val icon: ImageVector) {
    data object Home : BottomTab(Screen.Home.route, "Ana Sayfa", Icons.Default.Home)
    data object Favorites : BottomTab(Screen.Favorites.route, "Favoriler", Icons.Default.Favorite)
    data object MyCart : BottomTab(Screen.MyCart.route, "Sepetim", Icons.Default.ShoppingCart)
    data object Category : BottomTab(
        Screen.Category.route, "Kategoriler",
        Icons.AutoMirrored.Filled.List
    )

    data object Profile : BottomTab(Screen.Profile.route, "Profil", Icons.Default.AccountCircle)
}

val bottomTabs = listOf(
    BottomTab.Home,
    BottomTab.Favorites,
    BottomTab.MyCart,
    BottomTab.Category,
    BottomTab.Profile
)