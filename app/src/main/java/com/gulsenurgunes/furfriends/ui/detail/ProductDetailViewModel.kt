package com.gulsenurgunes.furfriends.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.cart.AddToCartUseCase
import com.gulsenurgunes.furfriends.domain.usecase.order.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    @Named("userId") private val userId: String,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailContract.UiState())
    val uiState: StateFlow<ProductDetailContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<ProductDetailContract.UiEffect>(Channel.BUFFERED)
    val uiEffect: Flow<ProductDetailContract.UiEffect> = _uiEffect.receiveAsFlow()

    private val productId: Int = savedStateHandle.get<Int>("productId")
        ?: throw IllegalArgumentException("productId parametresi null ya da Int değil")
    private val storeKey = "FurFriends"

    init {
        loadProductDetail()
    }

    private fun loadProductDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = getProductDetailUseCase(storeKey, productId)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(productDetail = result.data, isLoading = false)
                    }
                }

                is Resource.Error -> {
                    _uiEffect.send(ProductDetailContract.UiEffect.ShowToastMessage(result.message))
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
            }
        }
    }

    fun onAction(action: ProductDetailContract.UiAction) {
        when (action) {
            is ProductDetailContract.UiAction.BackButtonClick -> sendEffect(ProductDetailContract.UiEffect.NavigateBack)
            is ProductDetailContract.UiAction.ShareProduct -> {
                val product = _uiState.value.productDetail
                val shareText = "Check out this product: ${product?.title} - ₺${product?.price}"
                sendEffect(ProductDetailContract.UiEffect.ShareProduct(shareText))
            }
            is ProductDetailContract.UiAction.ToggleFavoriteClick -> {

            }
            is ProductDetailContract.UiAction.AddToCartClick -> {
                viewModelScope.launch {
                    val pid = action.productDetail.id ?: return@launch
                    Log.d("CART_FLOW", "add → uid=$userId, pid=$pid")

                    val result = addToCartUseCase(userId, pid)

                    if (result.isSuccess) {
                        Log.d("CART_FLOW", "add → success")
                        sendEffect(ProductDetailContract.UiEffect.ShowToastMessage("Ürün sepete eklendi"))
                        sendEffect(ProductDetailContract.UiEffect.NavigateToCart)
                    } else {
                        Log.e("CART_FLOW", "add → failed", result.exceptionOrNull())
                        sendEffect(ProductDetailContract.UiEffect.ShowToastMessage(result.exceptionOrNull()?.message ?: "Ekleme hatası"))
                    }
                }
            }

        }
    }

    private fun sendEffect(effect: ProductDetailContract.UiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

}
