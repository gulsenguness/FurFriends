package com.gulsenurgunes.furfriends.ui.profile.cards.savedcards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.navigation.Screen
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.auth.components.AuthButton

@Composable
fun SavedCardsScreen(
    onBackClick: () -> Unit = {},
    navController: NavController
    ) {
    val cards = remember { listOf(SavedCard(), SavedCard()) }
    var selectedOption by remember { mutableStateOf("upi") }
    var upiId by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Payment",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ){
            Row {
                Text("Credit/Debit Card", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(28.dp))
                OutlinedButton(onClick = {
                    navController.navigate(Screen.AddCards.route)
                }) {
                    Text("Add Card")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(cards) { card ->
                    CardView(card = card)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            SelectableOption(
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null
                    )
                },
                text = "Cash On Delivery (Cash/UPI)",
                onClick = { },
                selected = true
            )
            Spacer(modifier = Modifier.height(28.dp))
            ExpandablePaymentOption(
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                title = "Google Pay/Phone Pay/BHIM UPI",
                expanded = selectedOption == "upi",
                onOptionClick = { selectedOption = "upi" }
            ) {
                Text("Link Via UPI", style = MaterialTheme.typography.bodySmall)
                OutlinedTextField(
                    value = upiId,
                    onValueChange = { upiId = it },
                    placeholder = { Text("Enter Your UPI ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Continue", color = Color.White)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your UPI ID will be encrypted and is 100% safe with us.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            ExpandablePaymentOption(
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                title = "Payment/Wallet",
                expanded = selectedOption == "upi",
                onOptionClick = { selectedOption = "upi" }
            ) {
                Text("Link Your Wallet", style = MaterialTheme.typography.bodySmall)
                OutlinedTextField(
                    value = upiId,
                    onValueChange = { upiId = it },
                    placeholder = { Text("Enter Your UPI ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Continue", color = Color.White)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your UPI ID will be encrypted and is 100% safe with us.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            SelectableOption(
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null
                    )
                },
                text = "Netbanking",
                onClick = { },
                selected = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            AuthButton(
                "Continue",
                onClick = {}
            )
        }
    }
}

@Composable
fun SelectableOption(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon()
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            RadioButton(
                selected = selected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black
                )
            )
        }
    }
}

@Composable
fun ExpandablePaymentOption(
    icon: @Composable () -> Unit,
    title: String,
    expanded: Boolean,
    onOptionClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        OutlinedButton(
            onClick = onOptionClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.dp, Color.Transparent),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    icon()
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(title, fontWeight = FontWeight.Bold)
                }
                RadioButton(
                    selected = expanded,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                content()
            }
        }
    }
}



@Composable
fun CardView(card: SavedCard) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(card.cardType, style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(8.dp))
            Text(card.maskedNumber, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("EXP ${card.expDate}")
                Text("CVV ${card.cvv}")
            }
            Spacer(Modifier.height(4.dp))
            Text(card.name, style = MaterialTheme.typography.labelMedium)
        }
    }
}


data class SavedCard(
    val cardType: String = "CREDIT CARD",
    val maskedNumber: String = "**** **** **** 4532",
    val name: String = "ROOPA SMITH",
    val expDate: String = "14/07",
    val cvv: String = "012"
)