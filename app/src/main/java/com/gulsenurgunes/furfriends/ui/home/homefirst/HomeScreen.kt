package com.gulsenurgunes.furfriends.ui.home.homefirst

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.navigation.TopBar

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val snackHost = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { eff ->
            when (eff) {
                is HomeContract.UiEffect.ShowError -> snackHost.showSnackbar(eff.message)
                is HomeContract.UiEffect.CategoryClick -> TODO()
                is HomeContract.UiEffect.ProductCartClick -> TODO()
                HomeContract.UiEffect.SearchClick -> TODO()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        topBar = { HomeTopBar { homeViewModel.onAction(HomeContract.UiAction.LoadHome) } }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()

                uiState.errorMessage != null ->
                    ErrorSection(uiState.errorMessage!!) {
                        homeViewModel.onAction(HomeContract.UiAction.LoadHome)
                    }

                else -> Home(navController)
            }

        }
    }
}


@Composable
fun HomeTopBar(onSearch: () -> Unit) = TopBar(
    title = {
        Image(
            painterResource(R.drawable.first),
            contentDescription = null,
            modifier = Modifier.size(32.dp).clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Text("Hello, Gülşen Güneş")
    },
    actions = {
        IconButton(onClick = {  }) {
            BadgedBox(badge = { Badge { Text("2") } }) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
        }
        IconButton(onClick = onSearch) {
            Icon(Icons.Default.Search, contentDescription = "Ara")
        }
    }
)

@Composable
private fun ErrorSection(msg: String, onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            msg,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) { Text("Tekrar Dene") }
    }
}




