package com.gulsenurgunes.furfriends.domain.model

data class AddressModel(
    val fullName: String = "",
    val phone: String = "",
    val pinCode: String = "",
    val addressLine: String = "",
    val locality: String = "",
    val city: String = "",
    val state: String = "",
    val type: String = ""
)

