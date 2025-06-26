package com.gulsenurgunes.furfriends.ui.profile.cards.addcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton

@Composable
fun AddCardScreen() {
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Add Cart",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AuthButton(
                    text = "Add Card",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Card( //Öylesine
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("CREDIT CARD", color = Color.White)
                    Text("**** **** **** 4532", color = Color.White, fontSize = 20.sp)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("EXP 14/07", color = Color.White)
                        Text("CVV 012", color = Color.White)
                    }
                    Text("ROOPA SMITH", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            OutlinedTextField(
                value = cardName,
                onValueChange = { cardName = it },
                label = { Text("Card Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = expDate,
                    onValueChange = { expDate = it },
                    label = { Text("Expiry Date") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}