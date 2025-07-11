package com.gulsenurgunes.furfriends.domain.model

data class AnimalLocation(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val addedBy: String = ""
)