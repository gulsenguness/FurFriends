package com.gulsenurgunes.furfriends.ui.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.domain.model.Category

@Composable
fun CategoryCollection(
    category: Category,
    onRemove: () -> Unit = {},
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick(category.name) }
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .height(220.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                    )
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    is AsyncImagePainter.State.Error -> {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "Placeholder",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    else -> SubcomposeAsyncImageContent()
                }
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = 8.dp)
                    .background(Color.White, shape = CircleShape)
                    .zIndex(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp,
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                    )
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = category.name,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
