package com.gulsenurgunes.furfriends.ui.profile.address.deliveryaddress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.fullpayment.checkout.CheckoutAddressCartSection
import com.gulsenurgunes.furfriends.ui.profile.address.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryAddress(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getAddresses()
    }
    val addresses = viewModel.addresses
    var selectedAddress by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Delivery Address") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            AuthButton(
                text = "Save Address",
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            addresses.forEach { address ->
                val icon = when (address.type) {
                    "Home" -> Icons.Default.Home
                    "Office" -> Icons.Default.LocationOn
                    "Shop" -> Icons.Default.ShoppingCart
                    else -> Icons.Default.Home
                }
                val fullAddress = listOfNotNull(
                    address.addressLine,
                    address.locality,
                    address.city,
                    address.state,
                    address.pinCode
                ).joinToString(", ")
                val isSelected = selectedAddress == fullAddress

                CheckoutAddressCartSection(
                    leadingIcon = icon,
                    lastIcon = Icons.Outlined.RadioButtonUnchecked,
                    title = "${address.type} Address",
                    subtitle = fullAddress,
                    isSelected = isSelected,
                    onClick = {
                        selectedAddress = fullAddress
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .clickable {}
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircleOutline,
                        contentDescription = "Add",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Add Address",
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

