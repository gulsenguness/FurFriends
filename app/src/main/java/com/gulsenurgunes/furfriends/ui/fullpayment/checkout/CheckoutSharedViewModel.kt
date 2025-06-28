package com.gulsenurgunes.furfriends.ui.fullpayment.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutSharedViewModel @Inject constructor() : ViewModel() {
    private var selectedAddress by mutableStateOf("")

    fun setAddress(address: String) {
        selectedAddress = address
    }
}