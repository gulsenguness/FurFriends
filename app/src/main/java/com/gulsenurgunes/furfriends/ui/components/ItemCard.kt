package com.gulsenurgunes.furfriends.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gulsenurgunes.furfriends.domain.model.ProductUi

@Composable
fun ItemCard(
    product: ProductUi,
    onClick: () -> Unit,
    onCartClick: () -> Unit,
    onTopRightIconClick: () -> Unit,
    isFavoriteMode: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = product.imageOne,
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Spacer(Modifier.height(8.dp))

                Text(
                    text = product.category,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )

                Spacer(Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${product.price}₺",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        if (product.salePrice > 0) {
                            Text(
                                text = "${product.salePrice}₺",
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black)
                            .clickable { onCartClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to cart",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(28.dp)
                    .background(Color.White, CircleShape)
                    .clickable { onTopRightIconClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavoriteMode) {
                        if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                    } else {
                        Icons.Default.Close
                    },
                    contentDescription = if (isFavoriteMode) "Toggle Favorite" else "Remove",
                    tint = if (isFavoriteMode && product.isFavorite) Color.Red else Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
