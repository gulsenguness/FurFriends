package com.gulsenurgunes.furfriends.ui.detail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gulsenurgunes.furfriends.navigation.Screen
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ProductDetailContract.UiEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is ProductDetailContract.UiEffect.ShowToastMessage -> {
                    Toast
                        .makeText(context, effect.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is ProductDetailContract.UiEffect.ShareProduct -> {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, effect.shareText)
                        type = "text/plain"
                    }
                    context.startActivity(
                        Intent.createChooser(intent, "Share via")
                    )
                }

                is ProductDetailContract.UiEffect.NavigateToCart -> {
                    navController.navigate(Screen.MyCart.route) { launchSingleTop = true }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }

                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.errorMessage != null -> {

                }
                uiState.productDetail != null -> {
                    val product = uiState.productDetail!!
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        val images = listOfNotNull(
                            product.imageOne,
                            product.imageTwo,
                            product.imageThree
                        )
                        product.isFavorite?.let {
                            ImageSection(
                                imageUrls = images,
                                isFavorite = product.isFavorite == true,
                                onFavoriteClick = {
                                    viewModel.onAction(ProductDetailContract.UiAction.ToggleFavoriteClick(product))
                                }
                            )
                        }

                        Spacer(Modifier.height(24.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            ProductTitle(
                                title = product.title.orEmpty(),
                                starts = product.rate.toString(),
                            )
                            Spacer(Modifier.height(16.dp))
                            ProductPriceQuantity(
                                price = "₺${product.price}",
                                salePrice = "₺${product.salePrice}",
                                quantity = 1
                            )
                            Spacer(Modifier.height(24.dp))
                            SelectableItemRow("Items Size :", listOf("S", "M", "L", "XL", "2XL"))
                            Spacer(Modifier.height(16.dp))
                            ColorSelectionRow()
                            Spacer(Modifier.height(24.dp))
                            Text(
                                "Description:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(product.description ?: "No description available.")
                            Spacer(Modifier.height(24.dp))
                            AuthButton(
                                text = "Add To Cart",
                                onClick = {
                                    viewModel.onAction(ProductDetailContract.UiAction.AddToCartClick(product))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    DetailScreen(navController = rememberNavController())
}