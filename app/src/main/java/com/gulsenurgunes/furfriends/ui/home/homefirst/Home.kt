package com.gulsenurgunes.furfriends.ui.home.homefirst

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.navigation.Screen
import com.gulsenurgunes.furfriends.ui.components.CategoryGrid
import com.gulsenurgunes.furfriends.ui.components.CategoryImage
import com.gulsenurgunes.furfriends.ui.components.DividerC
import com.gulsenurgunes.furfriends.ui.home.Comment
import com.gulsenurgunes.furfriends.ui.home.MiddleImages
import com.gulsenurgunes.furfriends.ui.home.PetGrooming
import com.gulsenurgunes.furfriends.ui.home.Section
import com.gulsenurgunes.furfriends.ui.home.Video
import com.gulsenurgunes.furfriends.ui.home.food.Food

@Composable
fun Home(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Section()
        CategoryGrid(
            text = "Find Best Category",
            categories = listOf(
                CategoryImage("Dogs", R.drawable.dogs, key = "Dogs"),
                CategoryImage("Cats", R.drawable.cats, key = "Cats"),
                CategoryImage("Rabbits", R.drawable.rabbits, key = "Rabbits"),
                CategoryImage("Parrot", R.drawable.parrot, key = "Parrot")
            ),
            onClick = { category ->
                navController.navigate(Screen.CategoryGroup.createRoute(category.key))
            },
            showIcon = false
        )
        PetGrooming()
        Video()
        DividerC()
        Food()
        MiddleImages()
    }
}
