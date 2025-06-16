package com.gulsenurgunes.furfriends.ui.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.R

@Composable
fun Video() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .background(Color(0xFFFFD54F), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "50%\nSale",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Image(
                    painter = painterResource(R.drawable.dogs),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
        Card(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFB74D)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "PLAY VIDEO",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.dogs),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }
}