package com.gulsenurgunes.furfriends.ui.map

import com.google.android.gms.maps.model.LatLng

object MapContract {

    data class UiState(
        val isLoading: Boolean = true,
        val location: LatLng? = null,
        val errorMessage: String? = null
    )

    sealed class UiAction {
        data object LoadMap : UiAction()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        data object LocationPermissionDenied : UiEffect()
    }
}