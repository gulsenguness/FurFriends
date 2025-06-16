package com.gulsenurgunes.furfriends.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulsenurgunes.furfriends.R

@Composable
fun PetGrooming() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.hairdresser),
                    contentDescription = "Pet Grooming",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Pet Grooming",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Adopt A Pet",
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.paw),
                    contentDescription = "Pet Grooming",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standart dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries",
            fontSize = 16.sp
        )
    }
}