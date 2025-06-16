package com.gulsenurgunes.furfriends.ui.mycart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gulsenurgunes.furfriends.domain.model.CartItem
import com.gulsenurgunes.furfriends.ui.components.DividerC

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