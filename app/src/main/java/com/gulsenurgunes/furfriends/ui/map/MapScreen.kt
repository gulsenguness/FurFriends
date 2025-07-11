package com.gulsenurgunes.furfriends.ui.map

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    animalViewModel: AnimalViewModel = hiltViewModel()
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val userLocation by mapViewModel.locationState.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        animalViewModel.loadAnimals()
    }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            mapViewModel.fetchUserLocation()
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(animals) {
        animals.firstOrNull()?.let { animal ->
            Log.d("MapsScreen", "Kamera odaklanıyor: ${animal.name}")
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(animal.latitude, animal.longitude),
                    12f
                ),
                durationMs = 1000
            )
        }
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = userLocation != null)
    ) {
        userLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Konumunuz",
                snippet = "Burası sizin yeriniz!"
            )
        }

        animals.forEach { animal ->
            Log.d("MapsScreen", "Marker çiziliyor: ${animal.name} → (${animal.latitude}, ${animal.longitude})")
            Marker(
                state = MarkerState(position = LatLng(animal.latitude, animal.longitude)),
                title = animal.name,
                snippet = "Tür: ${animal.type} - Ekleyen: ${animal.addedBy}"
            )
        }
    }
}
