package com.gulsenurgunes.furfriends.domain.repository

import com.gulsenurgunes.furfriends.domain.model.AddressModel

interface AddressRepository {
    fun saveAddress(address: AddressModel, onResult: (Boolean) -> Unit)
    fun getAddresses(onResult: (List<AddressModel>) -> Unit)
}