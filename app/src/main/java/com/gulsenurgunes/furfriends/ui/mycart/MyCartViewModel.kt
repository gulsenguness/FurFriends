package com.gulsenurgunes.furfriends.ui.mycart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.domain.model.CartItem
import com.gulsenurgunes.furfriends.domain.usecase.cart.ClearCartUseCase
import com.gulsenurgunes.furfriends.domain.usecase.cart.GetCartProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MyCartViewModel @Inject constructor(
    private val getCartProducts: GetCartProductsUseCase,
    private val clearCart: ClearCartUseCase,
    @Named("userId") private val userId: String
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyCartContract.UiState(isLoading = true))
    val uiState: StateFlow<MyCartContract.UiState> = _uiState.asStateFlow()
    private val _uiEffect = MutableSharedFlow<MyCartContract.UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        fetchCart()
    }

    fun onAction(action: MyCartContract.UiAction) = when (action) {
        MyCartContract.UiAction.LoadCart -> fetchCart()
        MyCartContract.UiAction.ClearCartClick -> clearCart()
        else -> Unit
    }

    private fun fetchCart() = viewModelScope.launch {
        _uiState.updateLoading(true)

        runCatching { getCartProducts(userId) }
            .onSuccess { items ->
                Log.d("CART_FLOW", "get  → success, size=${items.size}")
                if (items.isEmpty()) emitEffect(MyCartContract.UiEffect.ShowEmptyState)
                _uiState.updateSuccess(items)
            }
            .onFailure {
                Log.e("CART_FLOW", "get  → failed",)
                _uiState.updateError(it.message)
            }
    }

    private fun clearCart() = viewModelScope.launch {
        clearCart(userId)
        launchEffect { MyCartContract.UiEffect.ShowToastMessage("Sepet temizlendi") }
        fetchCart()
    }

    private fun emitEffect(effect: MyCartContract.UiEffect) =
        viewModelScope.launch { _uiEffect.emit(effect) }

    private fun launchEffect(effect: () -> MyCartContract.UiEffect.ShowToastMessage) =
        viewModelScope.launch { }

    private fun MutableStateFlow<MyCartContract.UiState>.updateLoading(flag: Boolean) {
        value = value.copy(isLoading = flag, errorMessage = null)
    }

    private fun MutableStateFlow<MyCartContract.UiState>.updateSuccess(items: List<CartItem>) {
        value = value.copy(
            isLoading = false,
            items = items,
            totalPrice = items.sumOf { (it.salePrice ?: it.price) * it.quantity }
        )
    }

    private fun MutableStateFlow<MyCartContract.UiState>.updateError(msg: String?) {
        value = value.copy(isLoading = false, errorMessage = msg ?: "Bilinmeyen hata")
    }
}