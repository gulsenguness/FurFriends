package com.gulsenurgunes.furfriends.ui.detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gulsenurgunes.furfriends.R

@Composable
fun ProductTitle(title: String, starts: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Default.Star,
            contentDescription = "Wishlist",
            tint = Color.Red,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = starts,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ProductPriceQuantity(
    price: String,
    salePrice: String,
    quantity: Int
) {
    var qty by remember { mutableIntStateOf(quantity.coerceAtLeast(1)) }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "Price :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
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
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Quantity :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { if (qty > 1) qty-- },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.remove),
                        contentDescription = "Decrease",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.width(4.dp))
                Text(
                    text = qty.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(4.dp))
                IconButton(
                    onClick = { qty++ },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Increase",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ImageSection(
    imageUrls: List<String>,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    val pagerState = rememberPagerState { imageUrls.size }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 8.dp

        ) { page ->
            val url = imageUrls[page]
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .padding(24.dp)
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
    }
}