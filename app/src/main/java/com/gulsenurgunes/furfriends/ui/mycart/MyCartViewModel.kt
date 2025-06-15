package com.gulsenurgunes.furfriends.ui.mycart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.domain.model.CartItem
import com.gulsenurgunes.furfriends.domain.usecase.cart.ClearCartUseCase
import com.gulsenurgunes.furfriends.domain.usecase.cart.DeleteCartItemUseCase
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
    private val deleteItem: DeleteCartItemUseCase,
    @Named("userId") private val userId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyCartContract.UiState(isLoading = true))
    val uiState: StateFlow<MyCartContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MyCartContract.UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        fetchCart()
    }

    fun onAction(action: MyCartContract.UiAction) {
        when (action) {
            is MyCartContract.UiAction.LoadCart -> fetchCart()
            is MyCartContract.UiAction.ClearCartClick -> clearCart()
            is MyCartContract.UiAction.DeleteItem -> deleteFromCart(action.id)
            is MyCartContract.UiAction.BackButtonClick -> emitEffect(MyCartContract.UiEffect.NavigateBack)
            is MyCartContract.UiAction.CheckoutClick -> emitEffect(MyCartContract.UiEffect.NavigateCheckout)
            is MyCartContract.UiAction.RemoveItemClick -> Unit
        }
    }

    private fun fetchCart() = launchWithHandling {
        _uiState.updateLoading(true)
        val result = runCatching { getCartProducts(userId) }
        result.fold(
            onSuccess = { items ->
                Log.d("CART_FLOW", "Fetched ${items.size} items")
                if (items.isEmpty()) emitEffect(MyCartContract.UiEffect.ShowEmptyState)
                _uiState.updateSuccess(items)
            },
            onFailure = { handleError(it, "Failed to fetch cart items") }
        )
    }

    private fun clearCart() = launchWithHandling {
        clearCart(userId)
        emitEffect(MyCartContract.UiEffect.ShowToastMessage("Sepet temizlendi"))
        fetchCart()  // cart'ı otomatik güncelle
    }

    private fun deleteFromCart(itemId: Int) = launchWithHandling {
        deleteItem(userId, itemId).onSuccess {
            val updatedItems = _uiState.value.items.filterNot { it.id == itemId }
            _uiState.value = _uiState.value.copy(items = updatedItems)
        }.onFailure {
            handleError(it, "Failed to delete item")
        }
    }

    // Ortak error handling
    private fun handleError(throwable: Throwable, logMessage: String) {
        Log.e("CART_VIEWMODEL", "$logMessage: ${throwable.message}")
        _uiState.updateError(throwable.message)
    }

    // Effect emit helper
    private fun emitEffect(effect: MyCartContract.UiEffect) {
        viewModelScope.launch { _uiEffect.emit(effect) }
    }


    private fun launchWithHandling(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                handleError(e, "Unexpected error")
            }
        }
    }

    private fun MutableStateFlow<MyCartContract.UiState>.updateLoading(isLoading: Boolean) {
        value = value.copy(isLoading = isLoading, errorMessage = null)
    }

    private fun MutableStateFlow<MyCartContract.UiState>.updateSuccess(items: List<CartItem>) {
        value = value.copy(
            isLoading = false,
            items = items,
            totalPrice = items.sumOf { (it.salePrice ?: it.price) * it.quantity }
        )
    }

    private fun MutableStateFlow<MyCartContract.UiState>.updateError(message: String?) {
        value = value.copy(isLoading = false, errorMessage = message ?: "Bilinmeyen hata")
    }

}