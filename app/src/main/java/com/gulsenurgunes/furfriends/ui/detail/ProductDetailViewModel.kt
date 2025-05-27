package com.gulsenurgunes.furfriends.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.GetProductDetailUseCase
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

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
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
                // Buraya favori işlemleri eklenecekse eklenebilir
                // _uiState.update { it.copy(...) }
            }
            is ProductDetailContract.UiAction.AddToCartClick -> {
                // Sepete ekleme işlemi (dummy logic şimdilik)
                sendEffect(ProductDetailContract.UiEffect.ShowToastMessage("Product added to cart"))
            }
        }
    }

    private fun sendEffect(effect: ProductDetailContract.UiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

}
