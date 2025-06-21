package com.gulsenurgunes.furfriends.ui.mycart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gulsenurgunes.furfriends.domain.model.CartItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gulsenurgunes.furfriends.ui.components.DividerC

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
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "My Cart",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "$itemCount items • Deliver to: ",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "London",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        fontSize = 14.sp
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                OutlinedButton(
                    onClick = onChangeAddress,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 1.dp)
                        .height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Change", fontSize = 12.sp)
                }
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
                .weight(1f)
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