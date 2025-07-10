package com.gulsenurgunes.furfriends.ui.map

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {
    private val _uiState = MutableStateFlow(MapContract.UiState())
    val uiState: StateFlow<MapContract.UiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MapContract.UiEffect>()
    val effect: SharedFlow<MapContract.UiEffect> = _effect

    fun onAction(action: MapContract.UiAction) {
        when (action) {
            is MapContract.UiAction.LoadMap -> {
                loadUserLocation()
            }
        }
    }

    private fun loadUserLocation() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        val permission = ContextCompat.checkSelfPermission(
            app, android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            viewModelScope.launch {
                Log.w("MapViewModel", "Konum izni verilmemiş.")
                _effect.emit(MapContract.UiEffect.LocationPermissionDenied)
            }
            return
        }
        Log.d("MapViewModel", "Konum izni mevcut, konum alınmaya çalışılıyor...")

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latLng = LatLng(location.latitude, location.longitude)
                Log.d("MapViewModel", "Konum başarıyla alındı: $latLng")
                _uiState.value = MapContract.UiState(isLoading = false, location = latLng)
            } else {
                Log.w("MapViewModel", "lastLocation null döndü, requestLocationUpdates deneniyor...")
                _uiState.value =
                    MapContract.UiState(isLoading = false, errorMessage = "Konum bulunamadı.")
            }
        }.addOnFailureListener { exception ->
            Log.e("MapViewModel", "Konum alınırken hata oluştu: ${exception.message}", exception)
            _uiState.value = MapContract.UiState(isLoading = false, errorMessage = "Konum alınamadı.")
        }

    }
}