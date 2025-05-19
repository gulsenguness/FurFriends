package com.gulsenurgunes.furfriends.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.common.Resource
import com.gulsenurgunes.furfriends.domain.usecase.GetProductsByCategoryUseCase
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
class FoodViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
) : ViewModel() {
    private val titleMap = mapOf(
        "Dogs"    to listOf("dog", "köpek"),
        "Cats"    to listOf("cat", "kedi"),
        "Rabbits" to listOf("rabbit", "tavşan"),
        "Parrot"  to listOf("parrot", "kuş")
    )

    private val _uiState = MutableStateFlow(FoodContract.UiState())
    val uiState: StateFlow<FoodContract.UiState> = _uiState.asStateFlow()

    private val _effect = Channel<FoodContract.UiEffect>(Channel.BUFFERED)
    val effect: Flow<FoodContract.UiEffect> = _effect.receiveAsFlow()

    init {
        onAction(FoodContract.UiAction.LoadFood)
    }

    fun onAction(action: FoodContract.UiAction) {
        when (action) {
            FoodContract.UiAction.LoadFood ->
                loadAllFood()
            is FoodContract.UiAction.SelectSubCategory ->
                selectSubCategory(action.category)
        }
    }

    private fun loadAllFood() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val res = getProductsByCategoryUseCase("FurFriends", "Nutrients")) {
                is Resource.Success -> {
                    val list = res.data
                    _uiState.update {
                        it.copy(
                            allProducts = list,
                            isLoading   = false
                        )
                    }
                    val current = _uiState.value.selectedSubCategory
                    if (current == null) {
                        val defaultCat = _uiState.value.subCategories.firstOrNull()
                        defaultCat?.let { selectSubCategory(it) }
                    }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _effect.send(FoodContract.UiEffect.ShowError(res.message))
                }
            }
        }
    }


    private fun selectSubCategory(category: String) {
        val keys = titleMap[category] ?: listOf(category)
        val filtered = _uiState.value.allProducts.filter { prod ->
            keys.any { key ->
                prod.title.contains(key, ignoreCase = true)
            }
        }
        _uiState.update {
            it.copy(
                selectedSubCategory = category,
                filteredProducts    = filtered
            )
        }
    }
}
