package com.gulsenurgunes.furfriends.domain.usecase.address

import com.gulsenurgunes.furfriends.domain.model.AddressModel
import com.gulsenurgunes.furfriends.domain.repository.AddressRepository
import javax.inject.Inject

class SaveAddressUseCase @Inject constructor(
    private val repo: AddressRepository
) {
    operator fun invoke(address: AddressModel, onResult: (Boolean) -> Unit) {
        repo.saveAddress(address, onResult)
    }
}
