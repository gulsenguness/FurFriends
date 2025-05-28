package com.gulsenurgunes.furfriends.ui.detail


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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
                else -> { /* Diğer efektler */ }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }

                },
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ){
            when{
                uiState.isLoading->{
                    CircularProgressIndicator()
                }
                uiState.errorMessage !=null -> {

                }
                uiState.productDetail !=null->{
                    val product = uiState.productDetail!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        ImageSection(
                            imageUrl = product.imageOne.orEmpty(),
                            isFavorite = true,
                            onFavoriteClick = {}
                        )
                        ProductTitle(title = product.title.orEmpty(),  starts = product.rate.toString())

                        Spacer(Modifier.height(16.dp))

                        ProductPriceQuantity(price = "₺${product.price}", salePrice = "₺${product.salePrice}", quantity = 1)

                        Spacer(Modifier.height(16.dp))

                        SelectableItemRow("Items Size", listOf("S", "M", "L", "XL", "2XL"))

                        Spacer(Modifier.height(16.dp))

                        ColorSelectionRow()

                        Spacer(Modifier.height(16.dp))

                        Text("Description:", fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)
                        Text(product.description ?: "No description available.")

                        Spacer(Modifier.height(24.dp))

                        AuthButton(text = "Add To Cart", onClick = {})
                    }
                }
            }
        }
    }
}

@Composable
fun SelectableItemRow(title: String, items: List<String>) {
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Text(title, style = MaterialTheme.typography.titleMedium)
    Row {
        items.forEach { item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (selectedItem == item) Color.Black else Color.White,
                        CircleShape
                    )
                    .border(1.dp, Color.Black, CircleShape)
                    .clickable { selectedItem = item }
            ) {
                Text(
                    text = item,
                    color = if (selectedItem == item) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
fun ColorSelectionRow() {
    var selectedColor by remember { mutableStateOf<Color?>(null) }
    Text("Items Color", style = MaterialTheme.typography.titleMedium)
    Row {
        listOf(Color.Blue, Color.Green, Color.Magenta,Color.Red,Color.Cyan).forEach { color ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = if (selectedColor == color) 2.dp else 0.dp,
                        color = if (selectedColor == color) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(5.dp)
                    .background(color, CircleShape)
                    .clickable { selectedColor = color }
            ) {
                if (selectedColor == color) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun ProductTitle(title: String, starts:String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.Star, contentDescription = "Wishlist")
        Text(text = starts, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ProductPriceQuantity(price: String,salePrice:String, quantity: Int) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Column {
            Text("Price", fontSize = 18.sp)
            Row {
                Text(
                    text = salePrice,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textDecoration = TextDecoration.None
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Quantity", fontSize = 18.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, "Minus")
                Text(quantity.toString())
                Icon(Icons.Default.AddCircle, "Plus")
            }
        }
    }
}

@Composable
private fun ImageSection(
    imageUrl: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.TopEnd
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .background(Color.White.copy(alpha = 0.8f), CircleShape)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    DetailScreen(navController = rememberNavController())
}