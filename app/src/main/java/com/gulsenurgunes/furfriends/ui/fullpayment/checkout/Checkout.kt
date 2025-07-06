package com.gulsenurgunes.furfriends.ui.fullpayment.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.components.DividerC
import com.gulsenurgunes.furfriends.ui.profile.address.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkout(
    viewModel: AddressViewModel = hiltViewModel()
) {
    val selectedAddress = viewModel.selectedAddress
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            AuthButton(
                text = "Submit Order",
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                CheckoutAddressCartSection(
                    leadingIcon = Icons.Default.LocationOn,
                    lastIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    title = "Delivery Address",
                    subtitle = selectedAddress ?: "No address selected",
                    onClick = { },
                    isSelected = false
                )
                DividerC()
                CheckoutAddressCartSection(
                    leadingIcon = Icons.Default.ShoppingCart,
                    lastIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    title = "Cart",
                    subtitle = "XXXX XXXX XXXX 3456",
                    onClick = { },
                    isSelected = false
                )
                DividerC()
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Additional Notes:",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                var notes by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("Write Here") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(24.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = false,
                    maxLines = 6
                )
                Spacer(Modifier.height(60.dp))
                SummaryRow(
                    left = "Bluebell Hand Block Tiered",
                    right = "2 X \$2000.00"
                )
                SummaryRow(
                    left = "Men Black Grey Allover Printed",
                    right = "2 X \$1699.00"
                )
                SummaryRow(
                    left = "Discount",
                    right = "-\$100.00",
                )
                SummaryRow(
                    left = "Shipping",
                    right = "FREE Delivery",
                    rightColor = Color(0xFF20A020)
                )
                Spacer(Modifier.height(6.dp))
                DividerC()
                Spacer(Modifier.height(6.dp))
                SummaryRow(
                    left = "My Order",
                    right = "\$3,599.00",
                    leftStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    rightStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                )
                Spacer(Modifier.height(75.dp))
            }
        }

    }
}

@Composable
fun CheckoutAddressCartSection(
    leadingIcon: ImageVector,
    lastIcon: ImageVector,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Black, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = if (isSelected) Icons.Filled.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isSelected) Color.Black else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SummaryRow(
    left: String,
    right: String,
    rightColor: Color = Color.Unspecified,
    leftStyle: androidx.compose.ui.text.TextStyle =
        MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
    rightStyle: androidx.compose.ui.text.TextStyle =
        MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp, textAlign = TextAlign.End)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = left,
            style = leftStyle,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = right,
            style = rightStyle.copy(color = rightColor.takeOrElse { rightStyle.color })
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CheckoutPreview() {
    Checkout()
}