package com.gulsenurgunes.furfriends.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.components.CategoryGrid
import com.gulsenurgunes.furfriends.ui.components.CategoryImage
import com.gulsenurgunes.furfriends.ui.components.Tittle

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val staticCategories = listOf(
        CategoryImage("Dogs", R.drawable.dogs),
        CategoryImage("Cats", R.drawable.cats),
        CategoryImage("Rabbits", R.drawable.rabbits),
        CategoryImage("Parrot", R.drawable.parrot),
    )
    Scaffold(
        topBar = {
            TopBar(
                title = {
                    Text("Category")
                },
            )
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Tittle(
                    "Set Your Wardrobe\\nWith Our Amazing Selection!",
                    false
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryGrid(
                    text = "",
                    categories = staticCategories,
                    onClick = {},
                    showIcon = false
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(Modifier.height(8.dp))
                Tittle("Discover Latest Collection", false)
            }
            when {
                state.isLoading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                state.errorMessage != null -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = state.errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                else -> {
                    items(state.categoryList) { category ->
                        CategoryCollection(category)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    CategoryScreen()
}