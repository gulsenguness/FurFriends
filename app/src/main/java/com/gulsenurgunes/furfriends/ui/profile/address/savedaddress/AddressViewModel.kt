package com.gulsenurgunes.furfriends.ui.profile.address.savedaddress

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gulsenurgunes.furfriends.domain.model.AddressModel
import com.gulsenurgunes.furfriends.domain.usecase.address.GetAddressesUseCase
import com.gulsenurgunes.furfriends.domain.usecase.address.SaveAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val saveAddress: SaveAddressUseCase,
    private val getAddresses: GetAddressesUseCase
) : ViewModel() {

    var addresses by mutableStateOf<List<AddressModel>>(emptyList())

    fun getAddresses() {
        getAddresses { list ->
            addresses = list
        }
    }

    fun saveAddress(address: AddressModel, onSaved: () -> Unit) {
        saveAddress(address) { success ->
            if (success) {
                getAddresses()
                onSaved()
            }
        }
    }

}

