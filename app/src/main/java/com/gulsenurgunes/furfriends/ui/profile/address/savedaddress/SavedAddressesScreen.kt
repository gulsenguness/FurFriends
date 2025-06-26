package com.gulsenurgunes.furfriends.ui.profile.address.savedaddress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.domain.model.AddressModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedAddressesScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getAddresses()
    }
    var fullName by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var pinCode by rememberSaveable { mutableStateOf("") }
    var addressLine by rememberSaveable { mutableStateOf("") }
    var locality by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var addressType by rememberSaveable { mutableStateOf("Home") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Delivery Address") },
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
                    val address = AddressModel(
                        fullName,
                        phone,
                        pinCode,
                        addressLine,
                        locality,
                        city,
                        state,
                        addressType
                    )
                    viewModel.saveAddress(address) {
                        navController.popBackStack()
                    }
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
            Text("Contact Details", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") })
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Mobile No.") })
            Spacer(Modifier.height(16.dp))
            Text("Address", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = pinCode, onValueChange = { pinCode = it }, label = { Text("Pin Code") })
            OutlinedTextField(value = addressLine, onValueChange = { addressLine = it }, label = { Text("Address") })
            OutlinedTextField(value = locality, onValueChange = { locality = it }, label = { Text("Locality/Town") })
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City/District") })
            OutlinedTextField(value = state, onValueChange = { state = it }, label = { Text("State") })
            Spacer(Modifier.height(16.dp))
            Text("Save Address As", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Home", "Shop", "Office").forEach { type ->
                    val selected = addressType == type
                    Button(
                        onClick = { addressType = type },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selected) Color.Black else Color.LightGray,
                            contentColor = if (selected) Color.White else Color.Black
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(type)
                    }
                }
            }
        }
    }
} 