package com.gulsenurgunes.furfriends.ui.home.food

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gulsenurgunes.furfriends.domain.model.ProductUi
import com.gulsenurgunes.furfriends.ui.components.DividerC

@Composable
fun Food(
    viewModel: FoodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            if (effect is FoodContract.UiEffect.ShowError) {
                snackbarHostState.showSnackbar(effect.message)
            }
        }
    }
    Column {
        FoodHeader()
        CategoryTabs()
        Spacer(Modifier.height(16.dp))
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.filteredProducts.isEmpty() -> {
                Text(
                    text = uiState.selectedSubCategory
                        ?.let { "No products found for \"$it\"" }
                        ?: "Please select a category",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }

            else -> {
                val real = uiState.filteredProducts.take(4)
                val displayList = real + List(4 - real.size) { null }

                displayList.chunked(2).forEach { row ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { product ->
                            FoodCard(
                                product = product,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun FoodHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Reliable Healthy Food\n For Your Pet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "See All",
        )
    }
}

@Composable
fun CategoryTabs(
    viewModel: FoodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.subCategories) { category ->
            val isSelected = category == uiState.selectedSubCategory
            OutlinedButton(
                onClick = {
                    viewModel.onAction(FoodContract.UiAction.SelectSubCategory(category))
                },
                modifier = Modifier.height(40.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (isSelected) Color.Black else Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected) Color.Black else Color.White,
                    contentColor = if (isSelected) Color.White else Color.Black
                )
            ) {
                Text(category)
            }
        }
    }
}

@Composable
fun FoodCard(product: ProductUi?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (product != null) {
                    AsyncImage(
                        model = product.imageOne,
                        contentDescription = product.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF5F5F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                }
            }
            DividerC()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp)
            ) {
                if (product != null) {

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${product.price}₺",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "${product.salePrice}₺",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    }
}

