package com.gulsenurgunes.furfriends.domain.usecase.address

import com.gulsenurgunes.furfriends.domain.model.AddressModel
import com.gulsenurgunes.furfriends.domain.repository.AddressRepository
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val repo: AddressRepository
) {
    operator fun invoke(onResult: (List<AddressModel>) -> Unit) {
        repo.getAddresses(onResult)
    }
}
