package com.gulsenurgunes.furfriends.ui.map

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private val DEFAULT_LOCATION = LatLng(41.0082, 28.9784)

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(viewModel: MapViewModel = hiltViewModel()) {
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            Log.d("MapsScreen", "İzin verilmiş, konum yükleniyor")
            viewModel.onAction(MapContract.UiAction.LoadMap)
        } else {
            Log.d("MapsScreen", "İzin verilmemiş, isteniyor")
            locationPermissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MapContract.UiEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
                MapContract.UiEffect.LocationPermissionDenied -> snackbarHostState.showSnackbar("Konum izni reddedildi!")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maps") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage ?: "Hata",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    MapContent(location = state.location)
                }
            }
        }
    }
}

@Composable
fun MapContent(
    location: LatLng?
) {
    val markerState = remember(location) {
        MarkerState(position = location ?: DEFAULT_LOCATION)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(DEFAULT_LOCATION, 14f)
    }

    LaunchedEffect(location) {
        location?.let {
            Log.d("MapContent", "Kamera konuma taşınıyor (move ile): $it")
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(it, 14f))
        }
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = location != null,
            mapType = MapType.NORMAL
        )
    ) {
        Marker(
            state = markerState,
            title = "Konumunuz",
            snippet = "Hoş geldiniz!"
        )
    }
}
