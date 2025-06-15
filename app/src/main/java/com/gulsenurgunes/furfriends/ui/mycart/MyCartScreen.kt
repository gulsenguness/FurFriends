package com.gulsenurgunes.furfriends.ui.mycart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gulsenurgunes.furfriends.domain.model.CartItem
import com.gulsenurgunes.furfriends.ui.components.DividerC

@Composable
fun MyCartScreen(
    viewModel: MyCartViewModel = hiltViewModel(),
    onChangeAddress: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MyCartContract.UiEffect.ShowToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is MyCartContract.UiEffect.NavigateBack -> onDismiss()
                is MyCartContract.UiEffect.NavigateCheckout -> { }
                is MyCartContract.UiEffect.ShowEmptyState -> { }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .clickable { onDismiss() }
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> Text(state.errorMessage ?: "Hata")
            state.items.isEmpty() -> Text("Sepetiniz boş 😿")
            else -> CartContent(
                items = state.items,
                totalPrice = state.totalPrice,
                itemCount = state.items.sumOf { it.quantity },
                onChangeAddress = onChangeAddress,
                onDelete = { item ->
                    viewModel.onAction(MyCartContract.UiAction.DeleteItem(item.id))
                }
            )
        }
    }
}


@Composable
fun CartContent(
    items: List<CartItem>,
    totalPrice: Double,
    itemCount: Int,
    onChangeAddress: () -> Unit,
    onQuantityChange: (String, Int) -> Unit = { _, _ -> },
    onDelete: (CartItem)->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "$itemCount items  •  Deliver to: ",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp,
            )
            Text(
                "London",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(26.dp))
            OutlinedButton(onClick = onChangeAddress, contentPadding = PaddingValues(4.dp)) {
                Icon(Icons.Default.Place, null, Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Change", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            "Subtotal: \$${"%.2f".format(totalPrice)}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(6.dp))
        DeliveryBadge("Your Order Is Eligible For Free Delivery")
        Spacer(Modifier.height(12.dp))
        DividerC()
        Spacer(Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(items, key = { it.id }) { item ->
                CartItemRow(
                    item = item,
                    onQuantityChange = onQuantityChange,
                    onDelete = { onDelete(item) }
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(
                "Proceed to Buy ($itemCount items)",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
        }
    }
}

@Composable
private fun DeliveryBadge(text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F6E9), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.CheckCircle,
            null,
            tint = Color(0xFF1BA672)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text,
            fontSize = 12.sp,
            color = Color(0xFF1BA672),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun CartItemRow(
    item: CartItem,
    onQuantityChange: (String, Int) -> Unit,
    onDelete: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = .1f)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(Modifier.weight(1f)) {
            Text(item.title, style = MaterialTheme.typography.bodyLarge)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("\$${item.price}", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(4.dp))
                item.salePrice?.let {
                    Text(
                        "\$${it}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Spacer(Modifier.width(4.dp))
                Icon(Icons.Default.Star, null, Modifier.size(14.dp))
                Text("(2k Review)", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                "FREE Delivery",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = { onDelete() }) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
    DividerC()
}