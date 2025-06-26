package com.gulsenurgunes.furfriends.ui.profile.address.deliveryaddress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.domain.model.AddressModel
import com.gulsenurgunes.furfriends.ui.profile.address.savedaddress.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryAddressScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val addresses = viewModel.addresses

    LaunchedEffect(Unit) {
        viewModel.getAddresses()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Delivery Address") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    navController.navigate("checkout")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Save Address")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (addresses.isEmpty()) {
                Text("No saved addresses found.")
            } else {
                addresses.forEach { address ->
                    DeliveryAddressCard(address)
                }
            }
        }
    }
}

@Composable
fun DeliveryAddressCard(address: AddressModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "${address.type} Address",
            fontWeight = FontWeight.Bold
        )
        Text("${address.fullName}, ${address.phone}")
        Text("${address.addressLine}, ${address.locality}")
        Text("${address.city}, ${address.state} - ${address.pinCode}")
    }
}

