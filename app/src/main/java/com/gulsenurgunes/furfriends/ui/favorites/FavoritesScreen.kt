package com.gulsenurgunes.furfriends.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.components.ItemCard

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    var isSearching by remember { mutableStateOf(false) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    Scaffold(
        topBar = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search favorites...") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            isSearching = false
                            viewModel.onSearchQueryChanged("")
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Kapat")
                        }
                    }
                )
            } else {
                TopBar(
                    title = {
                        Row  {
                            Text("Wishlist")
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSearching = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Ara")
                        }
                    }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            FavoritesItemSection(
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.onCategorySelected(it) }
            )
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
                }

                uiState.favoriteProducts.isEmpty() -> {
                    Text("Henüz favori ürün yok.")
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.favoriteProducts) { product ->
                            ItemCard(
                                product = product,
                                onClick = {
                                    navController.navigate("detail/${product.id}")
                                },
                                onCartClick = {
                                    viewModel.onAction(FavoriteContract.UiAction.AddToCart(product))
                                },
                                onTopRightIconClick = {
                                    viewModel.onAction(
                                        FavoriteContract.UiAction.DeleteFromFavorites(product.id)
                                    )
                                },
                                isFavoriteMode = false
                            )

                        }
                    }
                }
            }
        }
    }
}

