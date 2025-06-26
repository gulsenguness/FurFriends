package com.gulsenurgunes.furfriends.ui.fullpayment.deliveryaddress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton
import com.gulsenurgunes.furfriends.ui.fullpayment.checkout.CheckoutAddressCartSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryAddress(
    navController: NavController,
    viewModel: CheckoutSharedViewModel = hiltViewModel()
) {
    val addressList = listOf(
        "123 Main Street, Anytown, USA 12345",
        "456 Elm Avenue, Smallville, CA 98765",
        "789 Maple Lane, Suburbia, NY 54321",
        "654 Pine Road, Countryside, FL 34567"
    )

    var selectedAddress by rememberSaveable { mutableStateOf(addressList[0]) }
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
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }

    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ){
            Column {
                addressList.forEach { address ->
                    CheckoutAddressCartSection(
                        leadingIcon = Icons.Default.Home,
                        lastIcon = Icons.Default.Favorite,
                        title = "Address",
                        subtitle = address,
                        onClick = {
                            selectedAddress = address
                        }
                    )
                }
                var notes by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("Write Here") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(24.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = false,
                    maxLines = 6
                )
            }
        }
    }
}

