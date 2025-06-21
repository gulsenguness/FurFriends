package com.gulsenurgunes.furfriends.ui.categorygroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.navigation.Screen
import com.gulsenurgunes.furfriends.ui.components.ItemCard

@Composable
fun CategoryGroup(
    navController: NavController,
    viewModel: CategoryGroupViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val categoryName = viewModel.categoryKey

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = " $categoryName",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.products) { product ->
                        ItemCard(
                            product = product,
                            onClick = {
                                navController.navigate(Screen.Detail.createRoute(product.id))
                            },
                            onCartClick = {

                            },
                            onTopRightIconClick = {
                                viewModel.toggleFavorite(product)
                            },
                            isFavoriteMode = true
                        )
                    }
                }
            }
        }
    }
}

