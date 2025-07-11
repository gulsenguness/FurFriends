package com.gulsenurgunes.furfriends.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulsenurgunes.furfriends.domain.model.AnimalLocation
import com.gulsenurgunes.furfriends.domain.usecase.map.DeleteAnimalUseCase
import com.gulsenurgunes.furfriends.domain.usecase.map.GetAnimalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {

    private val _animals = MutableStateFlow<List<AnimalLocation>>(emptyList())
    val animals: StateFlow<List<AnimalLocation>> = _animals

    fun loadAnimals() {
        viewModelScope.launch {
            _animals.value = getAnimalsUseCase()
        }
    }
}
