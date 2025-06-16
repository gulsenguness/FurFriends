package com.gulsenurgunes.furfriends.ui.mycart

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MyCartScreen(
    viewModel: MyCartViewModel = hiltViewModel(),
    onChangeAddress: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MyCartContract.UiEffect.ShowToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is MyCartContract.UiEffect.NavigateBack -> onDismiss()
                is MyCartContract.UiEffect.NavigateCheckout -> { }
                is MyCartContract.UiEffect.ShowEmptyState -> { }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .clickable { onDismiss() }
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> Text(state.errorMessage ?: "Hata")
            state.items.isEmpty() -> Text("Sepetiniz boş 😿")
            else -> CartContent(
                items = state.items,
                totalPrice = state.totalPrice,
                itemCount = state.items.sumOf { it.quantity },
                onChangeAddress = onChangeAddress,
                onDelete = { item ->
                    viewModel.onAction(MyCartContract.UiAction.DeleteItem(item.id))
                }
            )
        }
    }
}